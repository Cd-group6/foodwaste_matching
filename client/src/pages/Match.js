import AsyncStorage from '@react-native-async-storage/async-storage';
import axios from "axios";
import React from "react";
import { useState,useEffect} from 'react';
import {
    widthPercentageToDP as wp,
    heightPercentageToDP as hp,
} from 'react-native-responsive-screen';
import {
    StyleSheet,
    Text,
    View,
    ScrollView,
    StatusBar,
    TouchableOpacity,
    Switch,
    TextInput,
    } from 'react-native';
import {theme} from "../colors.js";
import Search from '../components/SearchBar'

import Postcode from '@actbase/react-daum-postcode';

const Match = ({navigation}) => {

    const [size, setSize] = useState(false);
    const tSwitch1 = () => setSize(previousState => !previousState);

    const [trashCan, setTrashCan] = useState(false);
    const tSwitch2 = () => setTrashCan(previousState => !previousState);

    const [agree, setAgree] = useState(false);
    const tSwitch3 = () => setAgree(previousState => !previousState);

    const [postcode, setPostcode] = useState('');
    const [addr, setAddr] = useState('');
    const [extraAddr, setExtraAddr] = useState('');
    const [id, setId] = useState('');
    const matchSend = async (): Promise<void> => {
        try {
                axios.post("http://10.0.2.2:8080/api/matching", {
                    memberId: id, trashSize: size, trashOwn: trashCan, address: addr
                })
                  .then(response => {
                      console.log(response.data);

                  })
                  .catch(error => {
                    console.error(error);
                  });

              } catch (err) {
                console.error('login err', err);
              }
    };

    useEffect(() => {
          const getId = async () => {
              const idData = await AsyncStorage.getItem("id");
              console.log(JSON.stringify(await AsyncStorage.getItem("id")));
              if(idData) {
                  setId(idData);
              }
          }

          getId();


      }, []);
    return (
        <View style={styles.container}>
            <StatusBar style="auto" />
            <Search />
            <View style={styles.header}>
                <TouchableOpacity onPress={() => navigation.navigate('Chat')}>
                    <Text style={styles.textC}>채팅</Text>
                </TouchableOpacity>

                <Text style={styles.textM}>매칭</Text>
            </View>
            <ScrollView showsVerticalScrollIndicator={false}>

                <View style={styles.line}></View>

                <View style={styles.option1}>
                    <Text style={styles.opText}>봉투 규격(3L/5L)</Text>
                    <Text style={styles.stText}>{size ? '3L' : '5L'}</Text>
                    <Switch style={styles.opSwitch}
                        trackColor={{false: '#767577', true: theme.mainC}}
                        thumbColor={size ? theme.bg : '#f4f3f4'}
                        ios_backgroundColor="#3e3e3e"
                        onValueChange={tSwitch1}
                        value={size}/>
                </View>
                <View style={styles.option1}>
                    <Text style={styles.opText}>쓰레기 통</Text>
                    <Text style={styles.stText}>{trashCan ? '보유' : '미보유'}</Text>
                    <Switch style={styles.opSwitch}
                        trackColor={{false: '#767577', true: theme.mainC}}
                        thumbColor={trashCan ? theme.bg : '#f4f3f4'}
                        ios_backgroundColor="#3e3e3e"
                        onValueChange={tSwitch2}
                        value={trashCan}/>
                </View>
                <View style={styles.option1}>
                    <Text style={styles.opText}>보증금 활용 동의</Text>
                    <Text style={styles.stText}>{agree ? '동의' : '미동의'}</Text>
                    <Switch style={styles.opSwitch}
                        trackColor={{false: '#767577', true: theme.mainC}}
                        thumbColor={agree ? theme.bg : '#f4f3f4'}
                        ios_backgroundColor="#3e3e3e"
                        onValueChange={tSwitch3}
                        value={agree}/>
                </View>


                <View style={styles.number_text}>
                    <Text style={styles.text}></Text>
                </View>

                <View style={{ flex: 1, alignItems: 'center', justifyContent: 'center' }}>
                      <Postcode
                        style={{ width: '100%', height: 200 }}
                        jsOptions={{ animated: true }}
                        onSelected={(data) => {
                          setAddr('');
                          setExtraAddr('');
                          setPostcode(data.zonecode);
                          if (data.userSelectedType === 'R') {

                            setAddr(data.roadAddress);

                            if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                              setExtraAddr(data.bname);

                              if (data.buildingName !== '' && data.apartment === 'Y') {
                                setExtraAddr((prev) => {
                                  return prev !== '' ? `${prev}, ${data.buildingName}` : `${data.buildingName}`;
                                });
                              }
                            } else {
                              setExtraAddr('');
                            }
                          } else {
                            setExtraAddr(data.jibunAddress);
                          }
                        }}
                      />
                      <Text>우편번호:{postcode}</Text>
                      <Text>
                        도로명/지번 :{addr} ({extraAddr})
                      </Text>
                    </View>



                <TouchableOpacity
                    style={styles.btn}
                    onPress={() => {matchSend();}}>
                    <Text style={(styles.Text, {color: 'white'})}>매칭 시작</Text>
                </TouchableOpacity>
            </ScrollView>
        </View>
    );
}

const styles = StyleSheet.create({
       container: {
           flex: 1,
           backgroundColor: theme.bg,
           paddingHorizontal:20,

       },
       room:{
           backgroundColor:theme.chatBg,
           borderWidth:2,
           borderColor:theme.mainC,
           marginTop:30,
           paddingVertical:20,
           paddingHorizontal:20,
           borderRadius:10,
           flexDirection:"row",
           alignItems:"center",
       },
       chat:{
           backgroundColor:"white",
           paddingVertical:10,
           paddingHorizontal:20,
           alignItems:"center",
       },
       header:{
           justifyContent:"space-between",
           paddingHorizontal:55,
           flexDirection: "row",
           marginTop:27,

       },
       textC:{
           fontSize:25,
           fontWeight:"500",
           color:theme.grey,
       },
       textM:{
           fontSize:25,
           fontWeight:"500",
           color:theme.mainC,
       },
       line:{
            borderTopWidth:2,
            borderColor:theme.grey,
            marginTop:38,
            width: '100%',
       },
       option1:{
            flexDirection:"row",
            paddingTop: wp(14),
       },
       opText:{
            flex:1,
            fontSize: wp(4),
            color:"black",
       },
       stText:{
            fontSize: wp(3.6),
            color:"grey",
            justifyContent: 'flex-end',
            marginRight: wp(-43),
       },
       opSwitch:{
            flex:1,
       },
       btn: {
            flex: 1,
            width: '100%',
            height: hp(5),
            borderRadius: 10,
            justifyContent: 'center',
            alignItems: 'center',
            backgroundColor: theme.mainC,
            marginTop: wp(16),
       },
});
export  default Match
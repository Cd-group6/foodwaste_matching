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
                <TouchableOpacity
                                    style={styles.btn}
                                    onPress={() => navigation.navigate('Chat')}>
                                    <Text style={(styles.Text, {color: 'white'})}>매칭 시작</Text>
                                </TouchableOpacity>
                <TouchableOpacity
                    style={styles.btn}
                    onPress={() => navigation.navigate('Chat')}>
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
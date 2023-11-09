import React from "react";
import { useState,useEffect} from 'react';
import axios from "axios";
import {
    StyleSheet,
    Text,
    View,
    ScrollView,
    StatusBar,
    TouchableOpacity,
    Alert,
    } from 'react-native';
import {theme} from "../colors.js";
import AsyncStorage from '@react-native-async-storage/async-storage';
import {
    widthPercentageToDP as wp,
    heightPercentageToDP as hp,
} from 'react-native-responsive-screen';


const Setting = ({navigation}) => {
  const [name, setName] = useState('');
  const [id, setId] = useState('');
  const [email, setEmail] = useState('');
  const [deposit, setDeposit] = useState(0);

  const charge = () => {
      axios.post("http://34.64.146.16:8080/api/deposit/charge", {
          memberId: id,
      })
        .then(response => {
            console.log(response.data);
            setDeposit(response.data.deposit);
        })
        .catch(error => {
          console.error(error);
        });

  };
  const chargeCheck = () => {
      Alert.alert(
        '결제',
        '충전하시겠습니까? (900원)',
        [
          {text: '취소', onPress: () => {}, style: 'cancel'},
          {
            text: '확인',
            onPress: () => {
              charge();
            },
            style: 'charge',
          },
        ],
        {
          cancelable: true,
          onDismiss: () => {},
        },
      );
  };

  useEffect(() => {
      const getNameMail = async () => {
          const nameData = await AsyncStorage.getItem("name");
          console.log(JSON.stringify(await AsyncStorage.getItem("name")));
          if(nameData) {
              setName(nameData);
          }
      }
      const getEmail = async () => {
          const emailData = await AsyncStorage.getItem("email");

          if(emailData) {
              setEmail(emailData);
          }
      }
      getNameMail();
      getEmail();


  }, []);
  useEffect(() => {
      const getId = async () => {
          const idData = await AsyncStorage.getItem("id");

          if(idData) {
              setId(idData);
          }
      }

      getId();
  }, []);


  return (
      <View style={styles.container}>
          <StatusBar style="auto" />

          <ScrollView showsVerticalScrollIndicator={false}>
              <View style={styles.person}>
                <Text style={styles.textN}> {name.replaceAll("\"", "")}  </Text>
                <Text style={styles.textE}> {email.replaceAll("\"", "")}  </Text>
              </View>
              <View style={styles.room}>
                  <Text style={styles.textM}> 보증금  :</Text>
                  <Text style={styles.textMM}> {deposit}</Text>
              </View>
              <TouchableOpacity
                  style={styles.btn}
                  onPress={() => {chargeCheck()} }>
                  <Text style={(styles.Text, {color: 'white'})}>보증금 충전하기</Text>
              </TouchableOpacity>
              <View style={styles.notice}>
                  <Text style={styles.textNot1}>청주 음식물 쓰레기 처리 비용</Text>
                  <Text style={styles.textNot2}>3L 납부필증 : 180원</Text>
                  <Text style={styles.textNot2}>5L 납부필증 : 300원</Text>
                  <Text style={styles.textNot2}>보증금 1회 충전 시 900원 충전</Text>
              </View>
          </ScrollView>
      </View>
  );
};

const styles = StyleSheet.create({
    container: {
        flex: 1,
        backgroundColor: theme.bg,
        paddingHorizontal:20,

    },
    person:{
        paddingHorizontal:20,
        alignItems:"center",
    },
    room:{
        backgroundColor:theme.chatBg,
        borderWidth:2,
        borderColor:theme.mainC,
        marginTop:wp(10),
        paddingVertical:20,
        paddingHorizontal:50,
        borderRadius:10,
        flexDirection:"row",
        alignItems:"center",
        justifyContent:"space-between",
    },
    textN:{
        backgroundColor:"white",
        alignItems:"center",
        fontSize:22,
        fontWeight:"500",
        color: theme.grey,
        marginTop:wp(15),
        marginBottom:wp(-3),
    },
    textE:{
        backgroundColor:"white",
        alignItems:"center",
        fontSize:12,
        fontWeight:"500",
        color: theme.grey,
        marginTop:wp(5),
        marginBottom:wp(0),
    },
    textM:{
        backgroundColor:"white",
        paddingVertical:10,
        paddingHorizontal:40,
        alignItems:"center",
        fontSize:16,
        fontWeight:"500",
        color: theme.grey,
    },
    textMM:{
        backgroundColor:"white",
        paddingVertical:10,
        paddingHorizontal:20,
        alignItems:"center",
        fontSize:25,
        fontWeight:"500",
        color: theme.grey,
    },
    btn: {
         flex: 1,
         width: '100%',
         height: hp(5),
         borderRadius: 10,
         justifyContent: 'center',
         alignItems: 'center',
         backgroundColor: theme.mainC,
         marginTop: wp(15),
    },
    notice:{
        paddingHorizontal:20,
        alignItems:"center",
    },
    textNot1:{
        paddingVertical:10,
        paddingHorizontal:40,
        alignItems:"center",
        fontSize:18,
        fontWeight:"500",
        color: theme.grey,
        marginTop: wp(32),
    },
    textNot2:{
        paddingVertical:10,
        paddingHorizontal:40,
        alignItems:"center",
        fontSize:13,
        fontWeight:"500",
        color: theme.grey,
        marginTop: wp(-3),
    },
});

export default Setting
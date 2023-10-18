import React from "react";
import { useState,useEffect} from 'react';
import {
    StyleSheet,
    Text,
    View,
    ScrollView,
    StatusBar
    } from 'react-native';
import {theme} from "../colors.js";
import AsyncStorage from '@react-native-async-storage/async-storage';


const Setting = ({navigation}) => {
  const [name, setName] = useState('');
  const [email, setEmail] = useState('');
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


  return (
      <View style={styles.container}>
          <StatusBar style="auto" />

          <ScrollView showsVerticalScrollIndicator={false}>
              <View style={styles.room}>
                  <Text style={styles.chat}> 이름: {name}  이메일: {email} </Text>
              </View>
              <View style={styles.chat}></View>
              <View style={styles.chat}></View>
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
});

export default Setting
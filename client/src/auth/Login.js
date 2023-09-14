{/*import {
  widthPercentageToDP as wp,
  heightPercentageToDP as hp,
} from 'react-native-responsive-screen';
import {
    StyleSheet,
    Text,
    View,
    ScrollView,
    StatusBar,
    Image,
    Button,
    TouchableOpacity,
    TextInput,
    } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';

import * as React from 'react';
import { useState,useEffect} from 'react';
import {theme} from "../colors.js";
import img from '../assets/images/StartImg.png';





const Login = ({navigation}) => {
    return (
        <View style={styles.container}>
            <View style={styles.topArea}>
                <Image style={styles.img} source={img} />
                <Text style={styles.guide}>ID와 PASSWORD를 입력해주세요.</Text>
            </View>
            <View style={styles.formArea}>
                <Text style={styles.formText}>아이디</Text>
                <TextInput style={styles.textFormTop} placeholder={'ID'} />
                <Text style={styles.formText}>패스워드</Text>
                <TextInput style={styles.textFormBottom} placeholder={'PASSWORD'} />

            </View>
            <View style={{flex: 1}}>
                <View style={styles.btnArea}>
                    <TouchableOpacity
                        style={styles.btn}
                        onPress={() => navigation.navigate('MainTab')}>
                    <Text style={(styles.Text, {color: 'white'})}>로그인</Text>
                    </TouchableOpacity>
                    <Text
                      style={styles.TextRegister}
                      onPress={() => navigation.navigate('Register')}>
                      처음이시라면 회원가입이 필요해요
                    </Text>
                </View>
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
   container:{
        flex:1,
        flexDirection: 'column',
        backgroundColor: 'white',
        paddingLeft: wp(7),
        paddingRight: wp(7),
   },
   topArea: {
        flex: 1,
        paddingTop: wp(2),
   },
   guide:{
        flex:1,
        color:"black",
        marginTop:-124,
   },
   img:{
        flex:1.3,
        marginTop: 0,
        width:'150%',
        resizeMode: 'contain',
   },
   formArea: {
        justifyContent: 'center',
        marginTop: -150,
        paddingTop: wp(10),
        flex: 1.5,
     },
   textFormTop: {
        borderWidth: 2,
        borderBottomWidth: 2,
        borderColor: 'black',
        borderRadius:10,
        width: '100%',
        height: hp(6),
        paddingLeft: 15,
        paddingRight: 10,
   },
   textFormBottom: {
        borderWidth: 2,
        borderTopWidth: 2,
        borderColor: 'black',
        borderRadius:10,
        width: '100%',
        height: hp(6),
        paddingLeft: 15,
        paddingRight: 10,
   },
   formText:{
        paddingBottom: 6,
        paddingTop:20,
        paddingLeft:7,
        color:"black",
   },
   btnArea: {
        height: hp(9),
        justifyContent: 'center',
        alignItems: 'center',
        paddingBottom: hp(1.5),
        marginTop: -20,
   },
   btn: {
        flex: 1,
        width: '100%',
        borderRadius: 10,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: theme.mainC,
   },
});


export  default Login*/}

import * as React from 'react';
import { useState,useEffect} from 'react';
import {
    StyleSheet,
    Text,
    View,
    ScrollView,
    StatusBar,
    Image,
    Button,
    TouchableOpacity,
    ActivityIndicator,
    } from 'react-native';

import {
    widthPercentageToDP as wp,
    heightPercentageToDP as hp,
    } from 'react-native-responsive-screen';

import {theme} from "../colors.js";
import Logo from '../assets/images/Logo.png';
import Simg from '../assets/images/StartImg.png';

const Login = ({navigation}) => {

    return (
        <ScrollView style={styles.container}>
            <StatusBar style="auto" />
            <View style={styles.logoCon}>
                <Image style={styles.logo} source={Logo} />
                <Text style={styles.expl}>
                    실시간 매칭 음식물 쓰레기 처리 공유 플랫폼
                </Text>
                <Image style={styles.simg} source={Simg} />
                <View style={styles.btnArea}>
                    <TouchableOpacity style={styles.btn}
                        onPress={() => navigation.navigate('MainTab')}>
                        <Text style={styles.btnText}>카카오로 로그인</Text>
                    </TouchableOpacity>
                </View>
            </View>
        </ScrollView>
    );
};

const styles = StyleSheet.create({
       container: {
          backgroundColor: theme.bg,
          paddingHorizontal:20,
       },
       logoCon:{
          alignItems:"center",
       },
       logo: {
          marginTop: 30,
          width:350,
          resizeMode: 'contain',
       },
       expl: {
          marginTop:10,
       },
       simg: {
          marginTop:-250,
          width:300,
          resizeMode: 'contain',
       },
       btnArea: {
          marginTop:-260,
          width:200,
       },
       btn: {
         width: 200,
         height:40,
         borderRadius: 5,
         justifyContent: 'center',
         alignItems: 'center',
         backgroundColor: "#fef01b",
       },
       btnText: {
         color: 'black',
         justifyContent: 'center',
         alignItems: 'center',
       },
       activityIndicator: {
         alignItems: 'center',
         height: 80,
         marginTop:-200,
       },
   });

export default Login
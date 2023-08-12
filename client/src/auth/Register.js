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
    Image,
    Button,
    TouchableOpacity,
    TextInput,
    } from 'react-native';
import AsyncStorage from '@react-native-async-storage/async-storage';

import * as React from 'react';
import { useState,useEffect} from 'react';
import {theme} from "../colors.js";

const Register = ({navigation}) => {
    return (
        <View style={styles.container}>
            <View style={styles.topArea}>
                <Text style={styles.guide}>회원가입</Text>
            </View>
            <View style={styles.formArea}>
                <Text style={styles.formText}>닉네임</Text>
                <TextInput style={styles.textForm} />
                <Text style={styles.formText}>아이디</Text>
                <TextInput style={styles.textForm} />
                <Text style={styles.formText}>비밀번호</Text>
                <TextInput style={styles.textForm} />
                <Text style={styles.formText}>비밀번호 확인</Text>
                <TextInput style={styles.textForm} />
                <Text style={styles.formText}>주소</Text>
                <TextInput style={styles.textForm} />
            </View>
            <View style={styles.btncontain}>
                <View style={styles.btnArea}>
                    <TouchableOpacity
                        style={styles.btn}
                        onPress={() => navigation.navigate('Login')}>
                        <Text style={(styles.Text, {color: 'white'})}>회원가입</Text>
                    </TouchableOpacity>
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
        flex: 0.5,
        paddingTop: wp(2),
   },
   guide:{
        flex:1,
        color:"black",
        marginTop:10,
        fontSize: wp(8),
   },
   formArea: {
        justifyContent: 'center',
        marginTop: 0,
        paddingTop: wp(0),
        flex: 4,
     },
   textForm: {
        borderWidth: 2,
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
   btncontain: {
        flex: 1,
        marginTop:0,
   },
   btnArea: {
        height: hp(9),
        justifyContent: 'center',
        alignItems: 'center',
        paddingBottom: hp(3.2),
        marginTop: 30,
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


export  default Register
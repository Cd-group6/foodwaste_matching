import {
    StyleSheet,
    Text,
    View,
    ScrollView,
    StatusBar,
    Image,
    Button,
    TouchableOpacity,
    } from 'react-native';
import * as React from 'react';
import { useState,useEffect} from 'react';
import {theme} from "../colors.js";

function Login() {
    return (
        <View style={styles.container}>
            <Text style={styles.guide}>로그인을 위해 id와 password를 입력해주세요.</Text>

        </View>
    );
}

const styles = StyleSheet.create({
   container:{
       flex:1,
       alignItems: 'center',
       justifyContent: 'center',
   },
   guide:{
       color:"black",
   },
});


export  default Login
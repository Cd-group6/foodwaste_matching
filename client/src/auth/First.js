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

import AsyncStorage from '@react-native-async-storage/async-storage';

import {theme} from "../colors.js";
import Logo from '../assets/images/Logo.png';
import Simg from '../assets/images/StartImg.png';

const FirstScreen = ({navigation}) => {
    const [animating, setAnimating] = useState(true);
    useEffect(() => {
        setTimeout(() => {
            setAnimating(false);
            AsyncStorage.getItem('user_id').then((value) =>
                navigation.replace(value === null ? 'Auth' : 'Auth'),//뒷 부분 변경 필
            );
        }, 5000);
    }, []);

    return (
        <ScrollView style={styles.container}>
            <StatusBar style="auto" />
            <View style={styles.logoCon}>
                <Image style={styles.logo} source={Logo} />
                <Text style={styles.expl}>
                    실시간 매칭 음식물 쓰레기 처리 공유 플랫폼
                </Text>
                <Image style={styles.simg} source={Simg} />
                <ActivityIndicator
                  animating={animating}
                  color="#D0747D"
                  size="large"
                  style={styles.activityIndicator}
                />
                {/*  //로그인 버튼
                <View style={styles.btnArea}>
                    <TouchableOpacity style={styles.btn}
                        onPress={() => navigation.navigate('Auth')}>
                      <Text style={styles.btnText}>시작</Text>
                    </TouchableOpacity>
                </View>*/}
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
          marginTop:-200,
          width:200,
       },
       btn: {
         width: 200,
         height:40,
         borderRadius: 5,
         justifyContent: 'center',
         alignItems: 'center',
         backgroundColor: theme.mainC,
       },
       btnText: {
         color: 'white',
         justifyContent: 'center',
         alignItems: 'center',
       },
       activityIndicator: {
         alignItems: 'center',
         height: 80,
         marginTop:-200,
       },
   });

export default FirstScreen
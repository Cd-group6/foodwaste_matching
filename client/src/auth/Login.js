import ResultView from './IntroView';

import AsyncStorage from '@react-native-async-storage/async-storage';
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
    SafeAreaView,
    } from 'react-native';

import {
  KakaoOAuthToken,
  KakaoProfile,
  loginWithKakaoAccount,
  getProfile as getKakaoProfile,
  login,
  logout,
  unlink,
} from '@react-native-seoul/kakao-login';

import {
    widthPercentageToDP as wp,
    heightPercentageToDP as hp,
    } from 'react-native-responsive-screen';

import {theme} from "../colors.js";
import Logo from '../assets/images/Logo.png';
import Simg from '../assets/images/StartImg.png';



const Login = ({navigation}) => {
    const [token, setToken] = useState(true);
    const [result, setResult] = useState(true);
    const [accessToken, setAccessToken] = useState(true);

    const signInWithKakao = async (): Promise<void> => {
      try {
        const token = await login();
        setToken(JSON.stringify(token));
        await AsyncStorage.setItem('accessToken', JSON.stringify(token));

      } catch (err) {
        console.error('login err', err);
      }
    };

    const signOutWithKakao = async (): Promise<void> => {
      const message = await logout();

      setResult(message);
    };

    const getProfile = async (): Promise<void> => {
      try {
        const profile = await getKakaoProfile();

        setResult(JSON.stringify(profile));    //profile.nickname
      } catch (err) {
        console.error('signOut error', err);
      }
    };

    const unlinkKakao = async (): Promise<void> => {
      const message = await unlink();

      setResult(message);
    };

    useEffect(() => {
        const getData = async () => {
            const storageData =
            	JSON.stringify(await AsyncStorage.getItem("accessToken"));
            if(storageData) {

                setAccessToken(storageData);
            }
        }
        // AsyncStorage에 저장된 데이터가 있다면, 불러온다.
        getData();
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
                <View style={styles.btnArea}>
                    <TouchableOpacity style={styles.btn}
                        onPress={() => navigation.navigate('MainTab')}>
                        <Text style={styles.btnText}>화면전환 임시버튼</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.btn}
                        onPress={() => {signInWithKakao();}}>
                        <Text style={styles.btnText}>카카오로 로그인</Text>
                    </TouchableOpacity>
                    <TouchableOpacity style={styles.btn}
                         onPress={() => getProfile()}>
                        <Text style={styles.btnText}>프로필 받기</Text>
                    </TouchableOpacity>


                    <ResultView result={accessToken} />

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

{/*
import {StyleSheet, Text, View, Button, SafeAreaView} from 'react-native';
import React from 'react';
import {
  KakaoOAuthToken,
  KakaoProfile,
  getProfile as getKakaoProfile,
  login,
  logout,
  unlink,
} from '@react-native-seoul/kakao-login';

export default function App() {
  const signInWithKakao = async (): Promise<void> => {
    const token: KakaoOAuthToken = await login();

    setResult(JSON.stringify(token));
  };

  const signOutWithKakao = async (): Promise<void> => {
    const message = await logout();

    setResult(message);
  };

  const getKakaoProfile = async (): Promise<void> => {
    const profile: KakaoProfile = await getProfile();

    setResult(JSON.stringify(profile));
  };

  const unlinkKakao = async (): Promise<void> => {
    const message = await unlink();

    setResult(message);
  };

  return (
    <SafeAreaView>
      <Button title="로그인" onPress={signInWithKakao} />
    </SafeAreaView>
  );
}*/}
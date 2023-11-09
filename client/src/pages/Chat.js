import ResultView from '../auth/IntroView';
import AsyncStorage from '@react-native-async-storage/async-storage';
import * as StompJs from "@stomp/stompjs";
import * as encoding from 'text-encoding';
import SockJS from "sockjs-client";
import React from "react";
import { useState,useEffect, useCallback, useRef} from 'react';
import {
    StyleSheet,
    Text,
    View,
    ScrollView,
    StatusBar,
    TouchableOpacity,
    Image,
    } from 'react-native';
import {theme} from "../colors.js";
import Search from '../components/SearchBar';

const Home = ({navigation}) => {


    //const ws = useRef(null);
    //ws.current = new WebSocket('ws://10.0.2.2:8080/ws/chat');
    //console.log(ws.current);
    /*
    const openS = () =>{
        ws.current.onopen = () => {
            console.log('connected')
        };
        ws.current.onopen();
    };*/
    /*
    const [str1, setStr1] = useState('');
    const [str2, setStr2] = useState('');
    */
    /*const sendMessage1 = () => {
        setStr1(JSON.stringify({
            type:"ENTER",
            roomId:"2cb62d96-09a4-4ca3-a9d4-7f95be0b694a",
            sender:"1",
            message:""
        }))
        console.log('1E');
        ws.current.send(str1);
        console.log('2E');
        setCnt(cnt+1);
        console.log({cnt});
    };
    const sendMessage2 = () => {
        setStr2(JSON.stringify({
            type:"TALK",
            roomId:"2cb62d96-09a4-4ca3-a9d4-7f95be0b694a",
            sender:"1",
            message:"hihi"
        }))
        console.log('1T');
        ws.current.send(str2);
        console.log('2T');
        setCnt(cnt+1);
        console.log({cnt});
    };
    */
    const [accessToken, setAccessToken] = useState(true);
    const [name, setName] = useState('');
    const [imgURL, setImgURL] = useState('');
    const [cnt, setCnt] = useState(0);
    const [room, setRoom] = useState('');
    /*
    useEffect(() => {
        console.log('send1')
        ws.current.onmessage = e => {
          const message = JSON.parse(e.data);
          console.log(message.message);
        };
        console.log('send2')

    }, [cnt]);*/
    useEffect(() => {
        const getName = async () => {
            setImgURL(await AsyncStorage.getItem("imgURL"));
            setName(await AsyncStorage.getItem("name"));
        }

        getName();

    }, []);

    useEffect(() => {
            const getRoom = async () => {
                setRoom(await AsyncStorage.getItem("roomId"));
            }
            getRoom();
            console.log({room});
    }, []);

    return (
        <View style={styles.container}>
            <StatusBar style="auto" />
            <Search />
            <View style={styles.header}>

                <Text style={styles.textc}>채팅</Text>

                <TouchableOpacity onPress={() => navigation.navigate('Match')}>
                    <Text style={styles.textm}>매칭</Text>
                </TouchableOpacity>
            </View>
            <View style={styles.line}></View>
            <View style={styles.person}>
                <Text style={styles.textH}>{name.replaceAll("\"", "")}</Text>
            </View>
            <ScrollView showsVerticalScrollIndicator={false}>
                <View style={styles.room}>
                    <TouchableOpacity style={styles.button} onPress={() => {if(room){navigation.navigate('Room')}}}>
                        <Text style={styles.textm}>채팅방</Text>
                    </TouchableOpacity>
                </View>
                {/*<View style={styles.room}>
                    <TouchableOpacity style={styles.button} onPress={() => {sendMessage1()}}>
                        <Text style={styles.textm}>보내기1</Text>
                    </TouchableOpacity>
                </View>
                <View style={styles.room}>
                    <TouchableOpacity style={styles.button} onPress={() => {sendMessage2()}}>
                        <Text style={styles.textm}>보내기2</Text>
                    </TouchableOpacity>
                </View>*/}



                <View style={styles.chat}></View>
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
           marginTop:20,
           paddingVertical:20,
           paddingHorizontal:20,
           borderRadius:10,
           flexDirection:"row",
           alignItems:"center",
           justifyContent: "center",
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
       textc:{
           fontSize:25,
           fontWeight:"500",
           color:theme.mainC,
       },
       textm:{
           fontSize:25,
           fontWeight:"500",
           color:theme.grey,
       },
       textH:{
           fontSize:18,
           fontWeight:"500",
           color:theme.grey,
           marginTop:20,
           alignItems:"center",
       },
       person:{
            alignItems:"center",
       },
       line:{
            borderTopWidth:2,
            borderColor:theme.grey,
            marginTop:38,
            width: '100%',
       },
       container1: {
           flex: 1,
           justifyContent: 'center',
           alignItems: 'center',
         },
         button1: {
           backgroundColor: 'blue',
           padding: 10,
           borderRadius: 5,
           color: 'white',
         },
         boxContainer1: {
           marginTop: 20,
         },
         box1: {
           width: 100,
           height: 100,
           backgroundColor: 'red',
         },

});

export  default Home
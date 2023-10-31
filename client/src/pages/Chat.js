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
import Search from '../components/SearchBar'

//const Stomp =require('stompjs');

const Home = ({navigation}) => {
    const [boxes, setBoxes] = useState([]);
    const addBox = () => {
      const newBox = <View key={boxes.length} style={styles.box1}></View>;
      setBoxes([...boxes, newBox]);
    };

    {/*const connect = () => {
        // 소켓 연결
        try {
          const clientData = new StompJs.Client({
            brokerURL: "ws://10.0.2.2:8080/stomp/chat",

            debug: function (str) {
              console.log(str);
            },
            reconnectDelay: 5000, // 자동 재 연결
            heartbeatIncoming: 4000,
            heartbeatOutgoing: 4000,
          });
          console.log("구독 직전");
          clientData.onConnect = function () {
             clientData.subscribe("/sub/message/" + "cc00573f-8116-48c7-896f-7b2795670a1a", callback);
           };

           clientData.activate(); // 클라이언트 활성화
           //changeClient(clientData); // 클라이언트 갱신
         } catch (err) {
           console.log(err);
         }
       };*/}
    const client = useRef({});

    const connect = () => {
      client.current = new StompJs.Client({
        brokerURL: 'ws://10.0.2.2:8080/stomp/chat',
        onConnect: () => {
          console.log('success');
          subscribe();
        },
      });
      client.current.activate();
    };


    //var sock = new SockJS('http://10.0.2.2:8080/stomp/chat');
    //let client = Stomp.over(sock);

    const [accessToken, setAccessToken] = useState(true);
    const [name, setName] = useState('');
    const [imgURL, setImgURL] = useState('');
    useEffect(() => {
        const getName = async () => {
            setImgURL(await AsyncStorage.getItem("imgURL"));
            setName(await AsyncStorage.getItem("name"));
        }

        getName();

        {/*client.connect({}, () =>{
            console.log('Connected : ' );
            client.send("/sub/join", {},JSON.stringify('1'))

                // Create Message

                client.send('/sub/message/${cc00573f-8116-48c7-896f-7b2795670a1a}',{},
                JSON.stringify({"type":"TALK","roomId":"cc00573f-8116-48c7-896f-7b2795670a1","sender":"1","message":"hihi"}))


        })*/}



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
                    <TouchableOpacity style={styles.button} onPress={() => {connect(); navigation.navigate('Room');}}>
                        <Text style={styles.textm}>채팅방</Text>
                    </TouchableOpacity>
                </View>

                <View style={styles.container1}>
                  <TouchableOpacity onPress={addBox}>
                    <Text style={styles.button1}>생성하기</Text>
                  </TouchableOpacity>

                  {boxes.map((box, index) => (
                    <View key={index} style={styles.boxContainer1}>
                      {box}
                    </View>
                  ))}
                </View>

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
import React, { useState, useCallback, useEffect, useReducer,useRef } from 'react'
import axios from "axios";
import {
  GiftedChat,
  IMessage,
  Send,
  SendProps,
  SystemMessage,
} from 'react-native-gifted-chat'
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Alert, Linking, Platform, StyleSheet, Text, View, ScrollView,StatusBar,TouchableOpacity,} from 'react-native'
import {theme} from "../colors.js";
import {
    widthPercentageToDP as wp,
    heightPercentageToDP as hp,
} from 'react-native-responsive-screen';

const Room=()=> {
    const [id, setId]=useState('');
    const [room, setRoom]=useState('');
    const [name, setName]=useState('');
    const [cnt, setCnt]=useState(0);
    const ws = useRef(null);
    const [rDeposit, setRDeposit]=useState(1800);

    const [messages, setMessages] = useState([]);

    useEffect(() => {
            ws.current = new WebSocket('ws://34.64.146.16:8080/ws/chat');
            ws.current.onopen = () => {
                console.log('connected')
                setCnt(cnt+1);
            };
            ws.current.onclose = () => {
                console.log("closed")
            }
            return () => {
                ws.current.close();
            };
    }, []);
    useEffect(() => {
            console.log('room: '+room);
            axios.post("http://34.64.146.16:8080/chatRoom/recall?roomId="+room.replaceAll("\"", "")).then(response => {
                 //console.log('res: '+JSON.stringify(response.data[1].message));
                 for(let i=0;response.data[i];i++){
                    console.log('i: '+i);
                    var appMessages = {
                      _id: response.data[i].id,
                      text: response.data[i].message,
                      //createdAt: new Date(),
                      user:{
                          _id: response.data[i].sender,
                          name: response.data[i].senderName,
                      },
                    }
                    setMessages(previousMessages => GiftedChat.append(previousMessages,appMessages))
                 }
            }).catch(error => {
              console.error(error);
            });
    }, [cnt]);

    useEffect(() => {
            const getId = async () => {
                setId(await AsyncStorage.getItem("id"));
            }
            getId();
            console.log({id});
    }, []);

    useEffect(() => {
            const getRoom = async () => {
                setRoom(await AsyncStorage.getItem("roomId"));
            }
            getRoom();
            console.log({room});
    }, []);
    useEffect(() => {
        const getName = async () => {
            setName(await AsyncStorage.getItem("name"));
        }

        getName();

    }, []);

    useEffect(() => {
        ws.current.onmessage = e => {
          const message = JSON.parse(e.data);
            console.log(message.id);
          var sentMessages = {
            _id: message.id,
            text: message.message,
            createdAt: new Date(),
            user:{
                _id: message.sender,
                name: message.sender,
            },
          }
          setMessages(previousMessages => GiftedChat.append(previousMessages,sentMessages))

          /*if(message.id!=id){
            setMessages(previousMessages => GiftedChat.append(previousMessages,sentMessages))
          }
          else{}*/

        };
    }, []);

    const sendEnter = () => {
        let sendingEnter = JSON.stringify({type: "ENTER", roomId: room.replaceAll("\"", "") , sender: id, senderName: name.replaceAll("\"", ""), message:"" });
        ws.current.send(sendingEnter);
        console.log(sendingEnter);
    };

    const onSend = useCallback((messages = []) => {
        let sendingTalk = JSON.stringify({type: "TALK", roomId: room.replaceAll("\"", ""),sender: id, senderName: name.replaceAll("\"", ""), message: messages[0].text});
        console.log('m: '+messages[0].text);
        console.log(sendingTalk);
        ws.current.send(sendingTalk);
        //setMessages(previousMessages =>GiftedChat.append(previousMessages, messages[0]),);
    }, [id, room])

    const discharge = () => {
        axios.post("http://34.64.146.16:8080/chatRoom/cutdeposit", {
            roomId: room.replaceAll("\"", "")
        })
          .then(response => {
              setRDeposit(response.data);
          })
          .catch(error => {
            console.error(error);
          });
    };

    return (
        <View style={styles.container}>
            <View style={styles.dep}>
                <TouchableOpacity
                    style={styles.depBtn}
                    onPress={() => {discharge();} }>
                    <Text style={(styles.Text, {color: 'white'})}>보증금 사용</Text>
                </TouchableOpacity>
                <Text style={styles.textMM}> 보증금: {rDeposit} </Text>
            </View>
            <GiftedChat
              messages={messages}
              onSend={messages => onSend(messages)}
              user={{
                _id: id,
              }}
            />
            <TouchableOpacity
                style={styles.enterBtn}
                onPress={() => {sendEnter();} }>
                <Text style={styles.enterText}>입장하기</Text>
            </TouchableOpacity>
        </View>
    )
}
const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#f5f5f5' },
  content: { backgroundColor: '#ffffff', flex: 1 },
  enterBtn: {
       flex: 0.08,
       width: '100%',
       height: hp(1),
       borderRadius: 10,
       justifyContent: 'center',
       alignItems: 'center',
       backgroundColor: theme.mainC,
  },
  dep:{
      paddingHorizontal:60,
      flexDirection:"row",
      alignItems:"center",
      justifyContent:"space-between",
  },
  depBtn: {
       flex: 0.6,
       height: hp(4),
       borderRadius: 10,
       justifyContent: 'center',
       alignItems: 'center',
       backgroundColor: theme.mainC,
  },
  textMM:{
      flex: 0.4,
      paddingVertical:10,
      paddingHorizontal:20,
      alignItems:"center",
      justifyContent: 'center',
      fontSize:15,
      fontWeight:"500",
      color: theme.grey,
  },
  enterText:{
      paddingVertical:10,
      paddingHorizontal:20,
      alignItems:"center",
      fontSize:18,
      fontWeight:"500",
      color: 'white',
  },
})
export default Room
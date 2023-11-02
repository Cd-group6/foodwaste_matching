import React, { useState, useCallback, useEffect, useReducer,useRef } from 'react'
import {
  GiftedChat,
  IMessage,
  Send,
  SendProps,
  SystemMessage,
} from 'react-native-gifted-chat'
import AsyncStorage from '@react-native-async-storage/async-storage';
import { Alert, Linking, Platform, StyleSheet, Text, View, ScrollView,StatusBar,TouchableOpacity,} from 'react-native'


const Room=()=> {
    const [id, setId]=useState('');
    const [room, setRoom]=useState('');
    const [cnt, setCnt]=useState(0);
    const ws = useRef(null);

    const [messages, setMessages] = useState([]);

    useEffect(() => {
            ws.current = new WebSocket('ws://34.64.146.16:8080/ws/chat');
            ws.current.onopen = () => {
                console.log('connected')
            };
            ws.current.onclose = () => {
                console.log("closed")
            }
            return () => {
                ws.current.close();
            };
    }, []);

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
        let sendingEnter = JSON.stringify({type: "ENTER", roomId: room.replaceAll("\"", "") , sender: id , message:"" });
        ws.current.send(sendingEnter);
        console.log(sendingEnter);
    };

    const onSend = useCallback((messages = []) => {
        let sendingTalk = JSON.stringify({type: "TALK", roomId: room.replaceAll("\"", ""),sender: id, message: messages[0].text});
        console.log('m: '+messages[0].text);
        console.log(sendingTalk);
        ws.current.send(sendingTalk);
        //setMessages(previousMessages =>GiftedChat.append(previousMessages, messages[0]),);



    }, [id, room])
    return (
        <View style={styles.container}>
            <GiftedChat
              messages={messages}
              onSend={messages => onSend(messages)}
              user={{
                _id: id,
              }}
            />
            <TouchableOpacity
                style={styles.btn}
                onPress={() => {sendEnter();} }>
                <Text style={(styles.Text, {color: 'white'})}>보내기</Text>
            </TouchableOpacity>
        </View>
    )
}
const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#f5f5f5' },
  content: { backgroundColor: '#ffffff', flex: 1 },
})
export default Room
import React from "react";
import { useState,useEffect} from 'react';
import {
    StyleSheet,
    Text,
    View,
    TouchableOpacity,
    TextInput,
    ScrollView,
    Alert,
    StatusBar
    } from 'react-native';
import {theme} from "./colors.js";

export default function App() {
    const[chatting, setChatting]=useState(true);
    const[text, setText]=useState("");
    const[toDos,setToDos]=useState({});
    const match = ()=>setChatting(false);
    const chat = ()=>setChatting(true);
    const onChangeText=(payload)=>setText(payload);

    return (
        <View style={styles.container}>
            <StatusBar style="auto" />
            <TextInput
                onChangeText={onChangeText}
                returnKeyType='done'
                value={text}
                placeholder={chatting ? "  Search Chat": "  Search Match"}
                style={styles.search}
            />
            <View style={styles.header}>
                <TouchableOpacity onPress={chat}>
                    <Text style={{...styles.btnText, color:chatting ? theme.mainC:theme.grey}}>채팅</Text>
                </TouchableOpacity>
                <TouchableOpacity onPress={match}>
                    <Text style={{...styles.btnText, color:!chatting ? theme.mainC:theme.grey}}>매칭</Text>
                </TouchableOpacity>
            </View>
            <ScrollView showsVerticalScrollIndicator={false}>
                <View style={styles.room}>
                    <Text style={styles.chat}> asdf </Text>
                </View>
                <View style={styles.chat}></View>
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
    header:{
        justifyContent:"space-between",
        paddingHorizontal:55,
        flexDirection: "row",
        marginTop:27,

    },
    btnText:{
        fontSize:25,
        fontWeight:"500",
    },
    search:{
        backgroundColor:"#F1F1F1",
        paddingVertical:15,
        paddingHorizontal: 20,
        borderRadius:30,
        marginVertical:20,
        marginTop:45,
        fontSize:18,
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
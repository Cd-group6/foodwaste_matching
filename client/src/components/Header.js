import React from "react";
import { useState,useEffect} from 'react';
import {
    StyleSheet,
    Text,
    View,
    TouchableOpacity,
    } from 'react-native';
import {theme} from "../colors.js";

export default function App() {
    const[chatting, setChatting]=useState(true);
    const match = ()=>setChatting(false);
    const chat = ()=>setChatting(true);

    return (
        <View style={styles.header}>
            <TouchableOpacity onPress={chat}>
                <Text style={{...styles.btnText, color:chatting ? theme.mainC:theme.grey}}>채팅</Text>
            </TouchableOpacity>
            <TouchableOpacity onPress={match}>
                <Text style={{...styles.btnText, color:!chatting ? theme.mainC:theme.grey}}>매칭</Text>
            </TouchableOpacity>
        </View>
        );}

const styles = StyleSheet.create({
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
});
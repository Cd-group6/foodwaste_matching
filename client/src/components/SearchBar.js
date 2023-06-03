import React from "react";
import { useState,useEffect} from 'react';
import {
    StyleSheet,
    TextInput,
    } from 'react-native';
import {theme} from "../colors.js";


export default function App() {
    const[chatting, setChatting]=useState(true);
    const[text, setText]=useState("");
    const match = ()=>setChatting(false);
    const chat = ()=>setChatting(true);
    const onChangeText=(payload)=>setText(payload);

    return(
        <TextInput
            onChangeText={onChangeText}
            returnKeyType='done'
            value={text}
            placeholder={chatting ? "  Search Chat": "  Search Match"}
            style={styles.search}
        />
    );
}

const styles = StyleSheet.create({
        search:{
            backgroundColor:theme.search,
            paddingVertical:15,
            paddingHorizontal: 20,
            borderRadius:30,
            marginVertical:20,
            marginTop:45,
            fontSize:18,
        },

});
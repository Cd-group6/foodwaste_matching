import React from "react";
import { useState,useEffect} from 'react';
import {
    StyleSheet,
    Text,
    View,
    ScrollView,
    StatusBar,
    TouchableOpacity,
    } from 'react-native';
import {theme} from "../colors.js";
import Search from '../components/SearchBar'

const Match = ({navigation}) => {

    return (
        <View style={styles.container}>
            <StatusBar style="auto" />
            <Search />
            <View style={styles.header}>
                <TouchableOpacity onPress={() => navigation.navigate('Chat')}>
                    <Text style={styles.textc}>채팅</Text>
                </TouchableOpacity>

                <Text style={styles.textm}>매칭</Text>

            </View>
            <ScrollView showsVerticalScrollIndicator={false}>
                <View style={styles.room}>
                    <Text style={styles.chat}> Match </Text>
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
       header:{
           justifyContent:"space-between",
           paddingHorizontal:55,
           flexDirection: "row",
           marginTop:27,

       },
       textc:{
           fontSize:25,
           fontWeight:"500",
           color:theme.grey,
       },
       textm:{
           fontSize:25,
           fontWeight:"500",
           color:theme.mainC,
       },
});

export  default Match
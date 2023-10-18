import ResultView from '../auth/IntroView';
import AsyncStorage from '@react-native-async-storage/async-storage';

import React from "react";
import { useState,useEffect, useCallback} from 'react';
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

const Home = ({navigation}) => {
    const [accessToken, setAccessToken] = useState(true);


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
            <ScrollView showsVerticalScrollIndicator={false}>
                <View style={styles.room}>
                    <TouchableOpacity onPress={() => navigation.navigate('Room')}>
                        <Text style={styles.textm}>chat</Text>
                    </TouchableOpacity>
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
           color:theme.mainC,
       },
       textm:{
           fontSize:25,
           fontWeight:"500",
           color:theme.grey,
       },
});

export  default Home
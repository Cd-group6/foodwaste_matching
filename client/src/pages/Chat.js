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
    Image,
    } from 'react-native';
import {theme} from "../colors.js";
import Search from '../components/SearchBar'

const Home = ({navigation}) => {
    const [boxes, setBoxes] = useState([]);

    const addBox = () => {
      const newBox = <View key={boxes.length} style={styles.box1}></View>;
      setBoxes([...boxes, newBox]);
    };

    const [accessToken, setAccessToken] = useState(true);
    const [name, setName] = useState('');
    const [imgURL, setImgURL] = useState('');
    useEffect(() => {
        const getName = async () => {
            setImgURL(await AsyncStorage.getItem("imgURL"));
            setName(await AsyncStorage.getItem("name"));
        }

        getName();


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
                    <TouchableOpacity style={styles.button} onPress={() => navigation.navigate('Room')}>
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
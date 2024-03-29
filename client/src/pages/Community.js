import React from "react";
      import { useState,useEffect} from 'react';
      import {
          StyleSheet,
          Text,
          View,
          ScrollView,
          StatusBar
          } from 'react-native';
      import {theme} from "../colors.js";
      import Search from '../components/SearchBar'
      import Header from '../components/Header'

      export default function App() {

          return (
              <View style={styles.container}>
                  <StatusBar style="auto" />
                  <Search />
                  <ScrollView showsVerticalScrollIndicator={false}>
                      <View style={styles.story}>
                          <Text style={styles.post}> 게시판_준비중 </Text>
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
             story:{
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
             post:{
                     backgroundColor:"white",
                     paddingVertical:10,
                     paddingHorizontal:20,
                     alignItems:"center",
                     color:"black",
                 },
         });
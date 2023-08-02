/*
import * as React from 'react';
import {theme} from "./colors.js";
import { NavigationContainer } from '@react-navigation/native';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';

import IconM from 'react-native-vector-icons/MaterialIcons';
import Icon from 'react-native-vector-icons/Ionicons';

import Home from "./pages/Home"
import Setting from "./pages/Setting"
import Community from "./pages/Community"

const Tab = createBottomTabNavigator();

export default function App() {
  return (
    <NavigationContainer>
      <Tab.Navigator
        initialRouteName="Home"
        screenOptions={{
            tabBarActiveTintColor: theme.mainC,
            tabBarInactiveTintColor: theme.grey,
            headerShown: false,
        }}>
        <Tab.Screen
            name="Home"
            component={Home}
            options={{
                title: 'Home',
                tabBarIcon: ({color, size}) => (
                    <Icon name="home" color={color} size={size} />
                ),
            }}
        />
        <Tab.Screen
            name="Community"
            component={Community}
            options={{
                title: 'Community',
                tabBarIcon: ({color, size}) => (
                    <IconM name="dashboard" color={color} size={size} />
                ),
            }}
        />
        <Tab.Screen
            name="Setting"
            component={Setting}
            options={{
                title: 'Setting',
                tabBarIcon: ({color, size}) => (
                    <Icon name="settings-outline" color={color} size={size} />
                ),
            }}
        />

      </Tab.Navigator>
    </NavigationContainer>
  );
}
*/
/*
import * as React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';
import { useState,useEffect} from 'react';
import {
    StyleSheet,
    Text,
    View,
    ScrollView,
    StatusBar,
    Image,
    Button,
    } from 'react-native';
import {theme} from "./colors.js";
import Logo from './assets/images/Logo.png';
import Simg from './assets/images/StartImg.png';

import Login from "./pages/Login"

export default function App() {

    return (
        <View style={styles.container}>
            <StatusBar style="auto" />
            <View style={styles.logoCon}>
              <Image style={styles.logo} source={Logo} />
              <Text style={styles.expl}>
                  실시간 매칭 음식물 쓰레기 처리 공유 플랫폼
              </Text>
              <Image style={styles.simg} source={Simg} />
            </View>
        </View>
    );
}

const styles = StyleSheet.create({
       container: {
           backgroundColor: theme.bg,
           paddingHorizontal:20,
       },
       logoCon:{
          alignItems:"center",
       },
       logo: {
          marginTop: 30,
          width:350,
          resizeMode: 'contain',
       },
       expl: {
          marginTop:10,
       },
       simg: {
          marginTop:-250,
          width:350,
          resizeMode: 'contain',
       },
   });
*/
import * as React from 'react';
import {Button, View, Text, ScrollView} from 'react-native';
import {NavigationContainer} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';
import Login from "./pages/Login"
import First from "./pages/First"



const Stack = createStackNavigator();

function App() {
    return (
        <NavigationContainer>
            <Stack.Navigator initialRouteName="First" screenOptions={{headerShown: false,}}>
                <Stack.Screen name="First" component={First}/>
                <Stack.Screen name="Login" component={Login}/>
            </Stack.Navigator>
        </NavigationContainer>
    );
}

export default App;

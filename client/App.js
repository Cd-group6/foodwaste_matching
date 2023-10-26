import * as React from 'react';
import {Button, View, Text, ScrollView} from 'react-native';
import {NavigationContainer} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';
import { createBottomTabNavigator } from '@react-navigation/bottom-tabs';

import Login from "./src/auth/Login"
import First from "./src/auth/First"
import Register from "./src/auth/Register"
import Chat from "./src/pages/Chat"
import Setting from "./src/pages/Setting"
import Community from "./src/pages/Community"

import Match from "./src/pages/Match"

import Room from "./src/pages/Room"

import {theme} from "./src/colors.js";
import IconM from 'react-native-vector-icons/MaterialIcons';
import Icon from 'react-native-vector-icons/Ionicons';

const Stack = createStackNavigator();
const Tab = createBottomTabNavigator();
const HomeStack = createStackNavigator();

const HomeStackScreen = () => {
    return(
        <HomeStack.Navigator initialRouteName="Chat">
            <HomeStack.Screen
                name="Chat"
                component={Chat}
                options={{headerShown: false}}/>

            <HomeStack.Screen
                name="Match"
                component={Match}
                options={{headerShown: false}}/>

            <HomeStack.Screen
                name="Room"
                component={Room}
                options={{title: '', headerShown: true}}/>

        </HomeStack.Navigator>
    );
};

const MainTab = ({navigation, route}) => {
    return(
        <Tab.Navigator
            initialRouteName="HomeStackScreen"
            screenOptions={{
                tabBarActiveTintColor: theme.mainC,
                tabBarInactiveTintColor: theme.grey,
                headerShown: false,
            }}>
            <Tab.Screen
                name="HomeStackScreen"
                component={HomeStackScreen}
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
    );
};

const Auth = () => {
    return (
        <Stack.Navigator>
            <Stack.Screen
                name="Login"
                component={Login}
                options={{title: ''}}/>

            <Stack.Screen
                name="Register"
                component={Register}
                options={{title: ''}}/>
        </Stack.Navigator>
    );
};

const App: () => React$Node = () => {
  return (
      <NavigationContainer>
          <Stack.Navigator initialRouteName="First">
              {/* SplashScreen which will come once for 5 Seconds */}
              <Stack.Screen
                  name="First"
                  component={First}
                  // Hiding header for Splash Screen
                  options={{headerShown: false}}/>
              {/* Auth Navigator: Include Login and Signup */}

              <Stack.Screen
                  name="Auth"
                  component={Auth}
                  options={{headerShown: false}}/>
              {/* Navigation Drawer as a landing page */}

              <Stack.Screen
                  name="MainTab"
                  component={MainTab}
                  // Hiding header for Navigation Drawer
                  options={{headerShown: false}}/>

          </Stack.Navigator>
      </NavigationContainer>
  );
};

export default App;

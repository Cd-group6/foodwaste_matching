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
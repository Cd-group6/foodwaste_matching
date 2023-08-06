import * as React from 'react';
import {Button, View, Text, ScrollView} from 'react-native';
import {NavigationContainer} from '@react-navigation/native';
import {createStackNavigator} from '@react-navigation/stack';
import Login from "./src/auth/Login"
import First from "./src/auth/First"
{/*import Register from "./src/auth/Register"*/}

const Stack = createStackNavigator();

const Auth = () => {
    return (
        <Stack.Navigator>
            <Stack.Screen
                name="Login"
                component={Login}
                options={{title: ''}}/>

            {/*<Stack.Screen
                name="Register"
                component={Register}
                options={{title: ''}}/>*/}
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
              {/*
              <Stack.Screen
                  name="DrawerNavigationRoutes"
                  component={DrawerNavigationRoutes}
                  // Hiding header for Navigation Drawer
                  options={{headerShown: false}}/>*/}
          </Stack.Navigator>
      </NavigationContainer>
  );
};

export default App;

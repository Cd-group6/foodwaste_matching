import React, { useState, useCallback, useEffect, useReducer } from 'react'
import {
  GiftedChat,
  IMessage,
  Send,
  SendProps,
  SystemMessage,
} from 'react-native-gifted-chat'
import { Alert, Linking, Platform, StyleSheet, Text, View } from 'react-native'
import Logo from '../assets/images/Logo.png';

import messagesData from './temp/messages'

const user = {
  _id: 1,
  name: 'Developer',
}

const ActionKind = {
  SEND_MESSAGE : 'SEND_MESSAGE',
  LOAD_EARLIER_MESSAGES : 'LOAD_EARLIER_MESSAGES',
  LOAD_EARLIER_START : 'LOAD_EARLIER_START',
  SET_IS_TYPING : 'SET_IS_TYPING',
}

interface StateAction {
  type: ActionKind,
  payload?: any
}

function reducer(state: IState, action: StateAction) {
  switch (action.type) {
    case ActionKind.SEND_MESSAGE: {
      return {
        ...state,
        step: state.step + 1,
        messages: action.payload,
      }
    }
    case ActionKind.LOAD_EARLIER_MESSAGES: {
      return {
        ...state,
        loadEarlier: true,
        isLoadingEarlier: false,
        messages: action.payload,
      }
    }
    case ActionKind.LOAD_EARLIER_START: {
      return {
        ...state,
        isLoadingEarlier: true,
      }
    }
    case ActionKind.SET_IS_TYPING: {
      return {
        ...state,
        isTyping: action.payload,
      }
    }
  }
}

const Room = () => {

    const [state, dispatch] = useReducer(reducer, {
      messages: messagesData,
      step: 0,
      loadEarlier: true,
      isLoadingEarlier: false,
      isTyping: false,
    })

    const onSend = useCallback(
      (messages: any[]) => {
        const sentMessages = [{ ...messages[0], sent: true, received: true }]
        //웹소켓 api불러오기
        const newMessages = GiftedChat.append(
          state.messages,
          sentMessages,
          Platform.OS !== 'web',
        )

        dispatch({ type: ActionKind.SEND_MESSAGE, payload: newMessages })
      },
      [dispatch, state.messages],
    )



    const onLongPressAvatar = useCallback((pressedUser: any) => {
      Alert.alert(JSON.stringify(pressedUser))
    }, [])

    const onPressAvatar = useCallback(() => {
      Alert.alert('On avatar press')
    }, [])



    const setIsTyping = useCallback(
      (isTyping: boolean) => {
        dispatch({ type: ActionKind.SET_IS_TYPING, payload: isTyping })
      },
      [dispatch],
    )

    const onSendFromUser = useCallback(
      (messages: IMessage[] = []) => {
        const createdAt = new Date()
        const messagesToUpload = messages.map(message => ({
          ...message,
          user,
          createdAt,
          _id: Math.round(Math.random() * 1000000),
        }))

        onSend(messagesToUpload)
      },
      [onSend],
    )

    const renderSystemMessage = useCallback(props => {
      return (
        <SystemMessage
          {...props}
          containerStyle={{
            marginBottom: 15,
          }}
          textStyle={{
            fontSize: 14,
          }}
        />
      )
    }, [])

    const renderSend = useCallback((props: SendProps<IMessage>) => {
      return (
        <Send {...props} containerStyle={{ justifyContent: 'center' }}>
          <Text>send</Text>
        </Send>
      )
    }, [])

    return (
        <View style={styles.container}>
          <View style={styles.content}>
            <GiftedChat
              messages={state.messages}
              onSend={onSend}
              loadEarlier={state.loadEarlier}
              isLoadingEarlier={state.isLoadingEarlier}
              user={user}
              scrollToBottom
              onLongPressAvatar={onLongPressAvatar}
              onPressAvatar={onPressAvatar}renderUsernameOnMessage={true}
              renderSystemMessage={renderSystemMessage}
              keyboardShouldPersistTaps='never'
              timeTextStyle={{
                left: { color: 'black' },
                right: { color: 'black' },
              }}
              infiniteScroll
            />
          </View>
        </View>
      )
}

const styles = StyleSheet.create({
  container: { flex: 1, backgroundColor: '#f5f5f5' },
  content: { backgroundColor: '#ffffff', flex: 1 },
})

export default Room

{/*
export default function Room() {
  const [messages, setMessages] = useState([])

  useEffect(() => {
    setMessages([
      {
        _id: 1,
        text: 'Hello developer',
        createdAt: new Date(),
        user: {
          _id: 2,
          name: 'React Native',
          avatar: Logo,
        },
      },
    ])
  }, [])

  const onSend = useCallback((messages = []) => {
    setMessages(previousMessages =>
      GiftedChat.append(previousMessages, messages),
    )
  }, [])

  return (
    <GiftedChat
      messages={messages}
      onSend={messages => onSend(messages)}
      user={{
        _id: 1,
      }}
    />
  )
}*/}


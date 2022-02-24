import React, { useEffect, useState } from 'react'
import styled, { css } from 'styled-components'
import { useForm } from 'react-hook-form'
import '../../test.css'

function SignupBox(props) {

  //java getter setter
  //정보 보호를 위해서 가져올때 get  쓸때 set
  //useState = 현재컴포넌트내부에서의 오브젝트 선언
  //ES6 내용이라 

  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [pwdCerti, setPwdCerti] = useState('')
  const [correct, setCorrect] = useState(false)
  const {
    register,
    handleSubmit,
    getValues,
    formState: { errors },
  } = useForm({ mode: 'onChange'})
  const onSubmit = (data) => console.log(data)

  return (
    <>
      <SignupBoxBg>
        <SignupBoxTitleStack>
          <SignupBoxTitle>
            <SignUp>Sign Up</SignUp>
            <WelcomeBack>Welcome Back!</WelcomeBack>
          </SignupBoxTitle>
        </SignupBoxTitleStack>
        <form onSubmit={handleSubmit(onSubmit)}>
          <input
            {...register('firstName', { required: '필수 항목입니다.' })}
            placeholder="이름"
          />
          <p>{errors.firstName?.message}</p>
          <input
            {...register('email', {
              required: '필수 항목입니다.',
              pattern: {
                value: /^\S+@\S+$/i,
                message: '이메일 형식에 맞춰 입력하세요.',
              },
            })}
            placeholder="이메일"
          />
          <p>{errors.email?.message}</p>
          <input
            {...register('password', {
              required: '필수 항목입니다.',
              minLength: {
                value: 8,
                message: '최소 8자리를 입력하세요.',
              },
              pattern: {
                value: /[`~!@@#$%^&*|₩₩₩'₩";:₩/?]/gi,
                message: '특수기호를 한 개 이상 넣어주세요.',
              },
            })}
            placeholder="비밀번호"
          />
          <p>{errors.password?.message}</p>
          <input
            {...register('passwordvaild', {
              required: '필수 항목입니다.',
              validate: (value) =>
                value === getValues('password') ||
                '비밀번호가 일치하지 않습니다.',
            })}
            placeholder="비밀번호 확인"
          />
          <p>{errors.passwordvaild?.message}</p>
          <button style={SendFormbtn}>전송하기</button>
        </form>
      </SignupBoxBg>
    </>
  )
}

const Container = styled.div`
  display: flex;
  flex-direction: column;
  position: absolute;
  top: 0;
  bottom: 0;
  justify-content: center;
  align-items: center;
`

const SignupBoxBg = styled.div`
  width: 562px;
  height: 858px;
  background-color: rgba(255, 255, 255, 1);
  border-radius: 29px;
  flex-direction: column;
  display: flex;
`

const SignupBoxTitle = styled.div`
  top: 2px;
  width: 298px;
  height: 88px;
  position: absolute;
  background-color: rgba(255, 255, 255, 1);
  border-radius: 15px;
  left: 0px;
  flex-direction: column;
  display: flex;
`

const WelcomeBack = styled.span`
  font-family: Roboto;
  font-style: normal;
  font-weight: 400;
  color: rgba(226, 63, 63, 1);
  font-size: 20px;
  margin-top: 59px;
  margin-left: 68px;
`

const SignUp = styled.span`
  font-family: Roboto;
  top: 0px;
  left: 81px;
  position: absolute;
  font-style: normal;
  font-weight: 400;
  color: rgba(249, 90, 90, 1);
  text-align: center;
  font-size: 40px;
`

const SignupBoxTitleStack = styled.div`
  width: 298px;
  height: 90px;
  margin-top: 38px;
  margin-left: 132px;
  position: relative;
`

const SendFormbtn = {
  fontFamily: 'Ubuntu',
  fontStyle: 'normal',
  fontWeight: 'normal',
  fontSize: '20px',
  lineHeight: '24px',
  width: '185px',
  height: '43px',
  backgroundColor: 'rgba(200,64,64,1)',
  borderRadius: '20px',
  marginTop: '10px',
}

export default SignupBox

import React, { useState, useEffect } from 'react'
import { signup, verify } from '../../api/test'
import styled from 'styled-components'
import { useNavigate } from 'react-router-dom'
// import TeamList from './TeamList';
// import UserList from './UserList';

export default function Signup() {
  const [input, setInput] = useState({
    email: '',
    name: '',
    password: '',
  })
  const [certoText, setCertiText] = useState('');
  // useEffect(() => { console.log(input) }, [input]);
  let navigate = useNavigate()
  const changeCheck = (e) => {
    const { name, value } = e.target
    setInput({
      ...input,
      [name]: value,
    })
  }
    const join = () => {
        signup(input, (response) => { console.log(response) }, (error) => { console.log(error) });
        verify({ "email": input.email },
        (response) => {
          setCertiText('certi', response.data);
        },
            (error) => {
                console.log("verify test 43444444");
                console.log(error)
            });
        navigate(`/signin`);
        // signin(input,
        //     (response) => { console.log(response) },    //success
        //     (error) => { console.log(error) });         //fail
    }
  return (
    <div>
      <section className="ftco-section">
        <div className="container">
          <div className="row justify-content-center">
            <div className="col-md-6 text-center mb-3 mt-4">
              <h2 className="heading-section" style={{ color: '#f46a72', fontFamily: 'Cafe24Ssurround', }}>
                SignUp
              </h2>
            </div>
          </div>
          <div className="row justify-content-center">
            <div className="col-md-6 col-lg-5"  style={{ backgroundColor: 'white'}}>
              <div className="login-wrap p-4 p-md-5">
                <div className="icon d-flex align-items-center justify-content-center">
                  <span className="fa fa-user-o"></span>
                </div>
                <h4 className="text-center mb-4" style={{ color: '#f46a72', fontFamily: 'Cafe24Ssurround', }}>
                  해당 정보를 입력해 주세요.
                </h4>
                <form action="#" className="login-form">
                  <br />
                  <div className="form-group d-flex justify-content-center">
                    <input
                      placeholder="이메일"
                      name="email"
                      onChange={changeCheck}
                      style={{fontFamily:'Cafe24SsurroundAir'}}
                    />
                  </div>
                  <br />
                  <div className="form-group d-flex justify-content-center ">
                    <input
                      placeholder="이름"
                      name="name"
                      onChange={changeCheck}
                      style={{fontFamily:'Cafe24SsurroundAir'}}
                    />
                  </div>
                  <br />
                  <div className="form-group d-flex justify-content-center">
                    <input
                      placeholder="비밀번호"
                      name="password"
                      onChange={changeCheck}
                      style={{WebkitTextSecurity: `disc`,fontFamily:'Cafe24SsurroundAir'}}
                    />
                  </div>

                  <hr />
                  <div className="form-group d-flex justify-content-center">
                    <button
                      onClick={join}
                      className="btn rounded submit p-2 px-5"
                      style={{
                        backgroundColor: '#f46a72',
                        textAlign: 'center',
                        fontFamily: 'Cafe24SsurroundAir',
                        color:'white'
                      }}
                    >
                      가입하기
                    </button>
                  </div>
                </form>
              </div>
            </div>
          </div>
        </div>
      </section>
    </div>
  )
}



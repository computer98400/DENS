import { store } from '../..'
import { API_BASE_URL } from '../../config'
import React, { useState, useEffect } from 'react'
import { Outlet, useParams, useNavigate } from 'react-router-dom'
import {
  profileTest,
  profileUpdate,
  putKeyword,
  ImgUpload,
  getKeyword,
} from '../../api/profile'
import { Container, Row, Stack } from 'react-bootstrap'
import ProfileGit from './Git'
import ProfileTagCloud from './TagCloud'
import ProfileImage from './Image'
import ProfileInfo from './Info'
import ProfileKeyword from './Keyword'
import axios from 'axios'
import { useCookies } from 'react-cookie'
import '../../css/profile.css'

export default function ProfileMain() {
  const [inputs, setInputs] = useState({
    name: '',
    position: '',
    stack: '  ',
    email: '',
    keyword: '',
    edit: false,
    gitId: '',
    git: true,
  })
  const { name, position, stack, email, edit, keyword, gitId, git } = inputs
  const [keywords, setKeywords] = useState([])
  const { id } = useParams()
  const [files, setFiles] = useState('')
  const [fileImage, setFileImage] = useState('')
  const [image, setImage] = useState('')
  const [cookies] = useCookies(['token'])
  const userId = store.getState().user.profileid

  const [idCheck, setIdCheck] = useState(false)

  const authAxios = axios.create({
    baseURL: API_BASE_URL,
    headers: {
      Authorization: `Bearer "${cookies.token}"`,
      withCredentials: true,
    },
  })
  useEffect(() => {
    if (userId === id) {
      setIdCheck(!idCheck)
    } else {
      setIdCheck(idCheck)
    }
  }, [userId])

  useEffect(() => {
    authAxios
      .get(`/profile/${id}`)
      .then((res) => {
        setInputs({
          ...inputs,
          name: res.data.name,
          position: res.data.position,
          stack: res.data.stack,
          email: res.data.email,
          git: !git,
          gitId: res.data.git_id,
        })
      })
      .catch((error) => console.log(error))
  }, [])
  useEffect(() => {
    getKeywords()
  }, [position, stack])

  function getKeywords() {
    authAxios
      .get(`/profile/keyword/${id}`)
      .then((res) => {
        const keywordObjs = res.data
        setKeywords(keywordPlus(keywordObjs))
      })
      .catch((error) => console.log(error))
  }
  function keywordPlus(e) {
    let words = []
    e.forEach((keywordObj) => {
      const word = {
        value: keywordObj.name,
        count: keywordObj.count,
      }
      words.push(word)
    })
    const value = e.reduce((max, p) => (p.count > max ? p.count : max), 0)
    const c = 1 + value
    words.push({ value: position, count: c }, { value: stack, count: c })
    return words
  }
  function update() {
    authAxios
      .put(`/profile/${id}`, {
        position: position,
        stack: stack,
        git_id: gitId,
      })
      .then((res) => {
        setInputs({
          ...inputs,
          name: res.data.name,
          position: res.data.position,
          stack: res.data.stack,
          email: res.data.email,
          gitId: res.data.git_id,
          git: !git,
          edit: !edit,
        })
      })
      .catch((error) => console.log(id))

    getKeywords()
  }

  function putKeywords() {
    authAxios
      .post(`/profile/keyword/${id}`, null, { params: { content: keyword } })
      .then((res) => {
        setKeywords([])
        getKeywords()
        console.log(keywords)
        setInputs({ ...inputs, keyword: '' })
      })
      .catch((error) => console.log(error))
  }

  function ImageUpload(e) {
    const formData = new FormData()
    formData.append('file', files[0])
    authAxios
      .post(`/profile/update/image/${id}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data',
        },
      })
      .then((res) => {
        setImage(res.data)
        setFiles('')
        window.location.replace(`/auth/profile/${id}`)
      })
      .catch((error) => console.log(error))
  }
  function onEdit() {
    setInputs({
      ...inputs,
      edit: true,
    })
  }
  function onSave(e) {
    const { value, name } = e.target
    setInputs({
      ...inputs,
      [name]: value,
    })
  }
  function onLoad(e) {
    setFiles(e.target.files)
    setFileImage(URL.createObjectURL(e.target.files[0]))
  }

  return (
    <div>
      <div className="container">
        <div className="main-body">
          <div class="row gutters-sm">
            <div class="col-md-4 mb-3">
              <ProfileImage
                id={id}
                fileImage={fileImage}
                userId={userId}
                onLoad={onLoad}
                ImageUpload={ImageUpload}
                authAxios={authAxios}
                idCheck={idCheck}
              />
              <ProfileInfo
                id={id}
                name={name}
                edit={edit}
                position={position}
                stack={stack}
                email={email}
                onSave={onSave}
                update={update}
                onEdit={onEdit}
                gitId={gitId}
                idCheck={idCheck}
              />
            </div>
            <div className="col-md-8">
              <ProfileTagCloud keywords={keywords} />

              <ProfileKeyword
                keyword={keyword}
                onSave={onSave}
                putKeywords={putKeywords}
              />

              <ProfileGit edit={edit} gitId={gitId} onSave={onSave} />
            </div>

            <Outlet />
          </div>
        </div>
      </div>
    </div>
  )
}

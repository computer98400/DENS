import { API_BASE_URL } from '../../config'
import React, { useState, useEffect } from 'react'
import { Outlet, useParams } from 'react-router-dom'
import Git from './Git'
import TagCloud from './TagCloud'
import Image from './Image'
import Info from './Info'
import Keyword from './Keyword'
import axios from 'axios'
import { useCookies } from 'react-cookie'
import '../../css/profile.css'
import { useStore } from 'react-redux'
export default function ProfileMain() {
  const store = useStore();
  const [inputs, setInputs] = useState({
    name: '',
    position: '',
    stack: ' ',
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
        onInputs()
      })
      .catch((error) => console.log(error))
  }, [])
  useEffect(() => {
    onKeywordsGet()
  }, [position, stack])

  function onInputs() {
    // setInputs({
    //   ...inputs,
    //   name: res.data.name,
    //   position: res.data.position,
    //   stack: res.data.stack,
    //   email: res.data.email,
    //   gitId: res.data.git_id,
    //   git: !git,
    // })
  }

  function onKeywordsGet() {
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
  function onUpdate() {
    authAxios
      .put(`/profile/${id}`, {
        position: position,
        stack: stack,
        git_id: gitId,
      })
      .then((res) => {
        setInputs({ edit: !edit })
        onInputs()
      })
      .catch((error) => console.log(id))
    onKeywordsGet()
  }

  function onKeywords() {
    authAxios
      .post(`/profile/keyword/${id}`, null, { params: { content: keyword } })
      .then((res) => {
        setKeywords([])
        onKeywordsGet()
        setInputs({ ...inputs, keyword: '' })
      })
      .catch((error) => console.log(error))
  }

  function onImageUpload(e) {
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
              <Image
                id={id}
                fileImage={fileImage}
                userId={userId}
                authAxios={authAxios}
                idCheck={idCheck}
                onLoad={onLoad}
                onImageUpload={onImageUpload}
              />
              <Info
                id={id}
                name={name}
                edit={edit}
                position={position}
                stack={stack}
                email={email}
                gitId={gitId}
                idCheck={idCheck}
                onSave={onSave}
                onUpdate={onUpdate}
                onEdit={onEdit}
              />
            </div>
            <div className="col-md-8">
              <TagCloud keywords={keywords} />

              <Keyword
                keyword={keyword}
                onSave={onSave}
                onKeywords={onKeywords}
              />

              <Git edit={edit} gitId={gitId} onSave={onSave} />
            </div>

            <Outlet />
          </div>
        </div>
      </div>
    </div>
  )
}

import React from 'react'
import { TagCloud } from 'react-tagcloud'
import {
  Container,
  Row,
  Button,
  Stack,
  Image,
  InputGroup,
  FormControl,
} from 'react-bootstrap'
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
export default function ProfileKeyword({ keyword, onSave, putKeywords }) {
  function enterKey(e) {
    if (e.key === 'Enter') {
      putKeywords()
    }
  }
  return (
    <div class="card mb-3">
      <div class="card-body">
        <Stack direction="horizontal" gap={3}>
          <InputGroup>
            <InputGroup.Text id="basic-addon1">#</InputGroup.Text>
            <FormControl
              name="keyword"
              placeholder="키워드를 입력해주세요."
              value={keyword}
              onChange={onSave}
              onKeyUp={enterKey}
            />
          </InputGroup>
        </Stack>
      </div>
    </div>
  )
}

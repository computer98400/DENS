import React from 'react'
import { Stack, InputGroup, FormControl } from 'react-bootstrap'
export default function ProfileKeyword({ keyword, onSave, onKeywords }) {
  function onCheck(e) {
    if (e.key === 'Enter') {
      onKeywords()
    }
  }
  return (
    <div className="card mb-3">
      <div className="card-body">
        <Stack direction="horizontal" gap={3}>
          <InputGroup>
            <InputGroup.Text id="basic-addon1">#</InputGroup.Text>
            <FormControl
              name="keyword"
              placeholder="키워드를 입력해주세요."
              value={keyword}
              onChange={onSave}
              onKeyUp={onCheck}
            />
          </InputGroup>
        </Stack>
      </div>
    </div>
  )
}

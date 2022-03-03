import React from 'react'
import { TagCloud } from 'react-tagcloud'

export default function ProfileTagCloud({ keywords }) {
  const options = {
    luminosity: 'bright',
    hue: 'pink',
  }
  return (
    <>
      <div class="card mb-3">
        <div class="card-body">
          <TagCloud
            minSize={10}
            maxSize={40}
            tags={keywords}
            style={{ width:'100%', textAlign: 'center' }}
            colorOptions={options}
          />
        </div>
      </div>
    </>
  )
}

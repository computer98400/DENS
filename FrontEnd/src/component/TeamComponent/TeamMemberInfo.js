import styled from "styled-components";

export default function TeamMemberInfo(props) {
  return (
    <div>
      <div>
        {props.name}
      </div>
      <div>
        {props.email}
      </div>
    </div>
  )
};
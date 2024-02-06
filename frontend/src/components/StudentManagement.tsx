import { useCallback, useEffect, useState } from 'react'
import { Box, Input, Button, Table, Thead, Tbody, Tr, Th, Td } from '@chakra-ui/react'
import { useQuery } from '@tanstack/react-query'
import { getStudents } from '../api/student.ts'
import type { Student } from '../types'

export function StudentManagement() {
  const [ students, setStudents ] = useState<Student[]>([])
  const [ studentId, setStudentId ] = useState<string>('')
  const [ selectedStudent, setSelectedStudent ] = useState<Student | undefined>()

  const getStudentsQuery = useQuery({
    queryKey: [ 'students-get' ],
    queryFn: () => getStudents()
  })

  useEffect(() => {
    if (getStudentsQuery.isSuccess) {
      // setStudents(getStudentsQuery.data)
      console.log('success')
      console.log(JSON.stringify(getStudentsQuery.data))
    }
  }, [ getStudentsQuery.isSuccess ])

  const studentName = useCallback((student: Student) => `${student.firstName} ${student.lastName}`, [])

  const findStudentById = useCallback((id: string) => ({ id, firstName: 'hello', lastName: 'there', email: 'abc@abc.com' }), [])

  const renderStudentTable = () => {
    return (
      <Table>
        <Thead>
          <Tr>
            <Th>ID</Th>
            <Th>Name</Th>
            {/* Add more columns as needed */}
          </Tr>
        </Thead>
        <Tbody>
          {students.map(student => (
            <Tr key={student.id}>
              <Td>{student.id}</Td>
              <Td>{studentName(student)}</Td>
              {/* Add more cells as needed */}
            </Tr>
          ))}
        </Tbody>
      </Table>
    );
  };

  return (
    <Box>
      <Input
        placeholder="Enter Student ID"
        value={studentId}
        onChange={(e) => setStudentId(e.target.value)}
      />
      <Button onClick={() => findStudentById('_testID_')}>Find Student</Button>
      {selectedStudent && (
        <Box>
          <p>Student ID: {selectedStudent.id}</p>
          <p>Name: {studentName(selectedStudent)}</p>
          {/* Display more student details as needed */}
        </Box>
      )}
      {renderStudentTable()}
    </Box>
  );
};

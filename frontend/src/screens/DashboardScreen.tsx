import { HStack } from '@chakra-ui/react'
import { CoursesTable } from '../components/course/CoursesTable'
import { StudentsTable } from '../components/student/StudentsTable'
import './DashboardScreen.css'

export function DashboardScreen() {
  return (
    <main className="container">
      <HStack align="stretch" gap={8}>
        <StudentsTable />
        <CoursesTable />
      </HStack>
    </main>
  )
}

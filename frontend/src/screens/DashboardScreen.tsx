import { HStack } from '@chakra-ui/react'
import { CoursesTable } from '../components/course/CoursesTable.tsx'
import { StudentsTable } from '../components/student/StudentsTable.tsx'
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

import { ReactNode } from 'react'
import { Routes, Route, Navigate } from 'react-router-dom'
import BmiView from './modules/bmi/views/BmiView'
import StatsView from './modules/stats/views/StatsView'
import AccountView from './modules/account/views/AccountView'

interface RouteConfig {
  path: string
  label: string
  element: ReactNode
}

export const routes: RouteConfig[] = [
  { path: "/bmi", label: "BMI", element: <BmiView /> },
  { path: "/stats", label: "Stats", element: <StatsView /> },
  { path: '/account', label: 'Account', element: <AccountView /> },
]

export default function AppRouter() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/account" replace />} />
      {routes.map((route) => (
        <Route key={route.path} path={route.path} element={route.element} />
      ))}
    </Routes>
  )
}

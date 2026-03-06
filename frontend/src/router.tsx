import { ReactNode } from 'react'
import { Routes, Route, Navigate } from 'react-router-dom'
import HelloView from './modules/hello/views/HelloView'
import EditMotdView from './modules/hello/views/EditMotdView'
import TimeView from './modules/time/views/TimeView'
import BmiView from "./modules/bmi/views/BmiView";
import StatsView from "./modules/stats/views/StatsView";

interface RouteConfig {
  path: string
  label: string
  element: ReactNode
}

export const routes: RouteConfig[] = [
  { path: '/hello', label: 'Hello', element: <HelloView /> },
  { path: '/hello/edit', label: 'Edit MOTD', element: <EditMotdView /> },
  { path: '/time', label: 'Time', element: <TimeView /> },
  { path: "/bmi", label: "BMI", element: <BmiView /> },
  { path: "/stats", label: "Stats", element: <StatsView /> },
]

export default function AppRouter() {
  return (
    <Routes>
      <Route path="/" element={<Navigate to="/hello" replace />} />
      {routes.map((route) => (
        <Route key={route.path} path={route.path} element={route.element} />
      ))}
    </Routes>
  )
}

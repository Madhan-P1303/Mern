import { ReactNode } from 'react';
import { Navigate, useLocation } from 'react-router-dom';
import { isAuthenticated } from '@/lib/auth';

interface ProtectedRouteProps {
  children: ReactNode;
  requireAuth?: boolean;
  allowedRoles?: string[];
}

const ProtectedRoute = ({ children, requireAuth = true, allowedRoles }: ProtectedRouteProps) => {
  const location = useLocation();
  const authenticated = isAuthenticated();
  const user = authenticated ? JSON.parse(localStorage.getItem('currentUser') || '{}') : null;

  // If authentication is required but user is not authenticated
  if (requireAuth && !authenticated) {
    return <Navigate to="/login" state={{ from: location }} replace />;
  }

  // If user is authenticated but trying to access login/signup pages
  if (!requireAuth && authenticated) {
    return <Navigate to="/dashboard" replace />;
  }

  // If role-based access is required
  if (allowedRoles && user && !allowedRoles.includes(user.role)) {
    return <Navigate to="/dashboard" replace />;
  }

  return <>{children}</>;
};

export default ProtectedRoute;









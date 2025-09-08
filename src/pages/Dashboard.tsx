import Navigation from "@/components/Navigation";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Progress } from "@/components/ui/progress";
import { Badge } from "@/components/ui/badge";
import { BookOpen, Clock, Trophy, Target, Play, CheckCircle, Loader2 } from "lucide-react";
import { Link } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import { dashboardAPI, enrollmentsAPI, achievementsAPI } from "@/lib/api-services";
import { getCurrentUser } from "@/lib/auth";

const Dashboard = () => {
  const currentUser = getCurrentUser();

  // Fetch dashboard data
  const { data: dashboard, isLoading: dashboardLoading, error: dashboardError } = useQuery({
    queryKey: ['dashboard'],
    queryFn: dashboardAPI.getDashboard,
    enabled: !!currentUser,
  });

  // Fetch recent enrollments
  const { data: enrollments, isLoading: enrollmentsLoading } = useQuery({
    queryKey: ['enrollments'],
    queryFn: enrollmentsAPI.getMyCourses,
    enabled: !!currentUser,
  });

  // Fetch achievements
  const { data: achievements, isLoading: achievementsLoading } = useQuery({
    queryKey: ['achievements'],
    queryFn: achievementsAPI.getMyAchievements,
    enabled: !!currentUser,
  });

  if (dashboardLoading) {
    return (
      <div className="min-h-screen bg-background">
        <Navigation />
        <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
          <div className="flex items-center justify-center h-64">
            <Loader2 className="h-8 w-8 animate-spin" />
          </div>
        </main>
      </div>
    );
  }

  if (dashboardError) {
    return (
      <div className="min-h-screen bg-background">
        <Navigation />
        <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
          <div className="text-center">
            <h1 className="text-2xl font-bold text-destructive">Error loading dashboard</h1>
            <p className="text-muted-foreground">Please try refreshing the page</p>
          </div>
        </main>
      </div>
    );
  }

  const user = dashboard || {
    userName: currentUser?.name || "User",
    totalEnrolledCourses: 0,
    completedCourses: 0,
    totalHours: 0,
    currentStreak: 0,
    totalAchievements: 0,
  };

  const enrolledCourses = enrollments?.slice(0, 3) || [];
  const recentAchievements = achievements?.slice(0, 4) || [];

  return (
    <div className="min-h-screen bg-background">
      <Navigation />
      
      <main className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Header */}
        <div className="mb-8">
          <h1 className="text-3xl font-bold mb-2">Welcome back, {user.userName}! 👋</h1>
          <p className="text-muted-foreground">
            Continue your learning journey and achieve your goals
          </p>
        </div>

        {/* Stats Cards */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          <Card className="bg-gradient-card border-0 shadow-card">
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Enrolled Courses</CardTitle>
              <BookOpen className="h-4 w-4 text-primary" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{user.totalEnrolledCourses}</div>
              <p className="text-xs text-muted-foreground">
                {user.completedCourses} completed
              </p>
            </CardContent>
          </Card>

          <Card className="bg-gradient-card border-0 shadow-card">
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Learning Hours</CardTitle>
              <Clock className="h-4 w-4 text-accent" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{user.totalHours}</div>
              <p className="text-xs text-muted-foreground">
                Total time spent
              </p>
            </CardContent>
          </Card>

          <Card className="bg-gradient-card border-0 shadow-card">
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Current Streak</CardTitle>
              <Target className="h-4 w-4 text-destructive" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">{user.currentStreak}</div>
              <p className="text-xs text-muted-foreground">
                Days in a row
              </p>
            </CardContent>
          </Card>

          <Card className="bg-gradient-card border-0 shadow-card">
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">Achievements</CardTitle>
              <Trophy className="h-4 w-4 text-yellow-500" />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold">
                {user.totalAchievements}
              </div>
              <p className="text-xs text-muted-foreground">
                Badges earned
              </p>
            </CardContent>
          </Card>
        </div>

        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Continue Learning */}
          <div className="lg:col-span-2">
            <h2 className="text-2xl font-semibold mb-6">Continue Learning</h2>
            <div className="space-y-6">
              {enrolledCourses.length > 0 ? (
                enrolledCourses.map((enrollment) => (
                  <Card key={enrollment.id} className="bg-gradient-card border-0 shadow-card hover:shadow-elegant transition-all duration-300">
                    <CardContent className="p-6">
                      <div className="flex items-start space-x-4">
                        <div className="w-16 h-16 bg-muted rounded-lg overflow-hidden flex-shrink-0 flex items-center justify-center">
                          <BookOpen className="h-8 w-8 text-muted-foreground" />
                        </div>
                        
                        <div className="flex-1 min-w-0">
                          <h3 className="font-semibold text-lg mb-2">{enrollment.courseTitle}</h3>
                          
                          <div className="flex items-center space-x-4 text-sm text-muted-foreground mb-3">
                            <span>Progress: {enrollment.progress}%</span>
                            <Badge variant={enrollment.completionStatus === 'COMPLETED' ? 'default' : 'secondary'}>
                              {enrollment.completionStatus}
                            </Badge>
                          </div>
                          
                          <Progress value={enrollment.progress} className="mb-3" />
                          
                          <div className="flex items-center justify-between">
                            <div>
                              <p className="text-sm text-muted-foreground">Status:</p>
                              <p className="font-medium">{enrollment.completionStatus}</p>
                            </div>
                            
                            <Link to={`/course/${enrollment.courseId}`}>
                              <Button className="bg-gradient-primary">
                                <Play className="h-4 w-4 mr-2" />
                                {enrollment.completionStatus === 'COMPLETED' ? 'Review' : 'Continue'}
                              </Button>
                            </Link>
                          </div>
                        </div>
                      </div>
                    </CardContent>
                  </Card>
                ))
              ) : (
                <Card className="bg-gradient-card border-0 shadow-card">
                  <CardContent className="p-6 text-center">
                    <BookOpen className="h-12 w-12 text-muted-foreground mx-auto mb-4" />
                    <h3 className="font-semibold text-lg mb-2">No enrolled courses yet</h3>
                    <p className="text-muted-foreground mb-4">Start your learning journey by enrolling in a course!</p>
                    <Link to="/courses">
                      <Button className="bg-gradient-primary">Browse Courses</Button>
                    </Link>
                  </CardContent>
                </Card>
              )}
              
              <Link to="/courses">
                <Button variant="outline" className="w-full">
                  Explore More Courses
                </Button>
              </Link>
            </div>
          </div>

          {/* Sidebar */}
          <div className="space-y-6">
            {/* Achievements */}
            <Card className="bg-gradient-card border-0 shadow-card">
              <CardHeader>
                <CardTitle className="flex items-center">
                  <Trophy className="h-5 w-5 mr-2 text-yellow-500" />
                  Achievements
                </CardTitle>
              </CardHeader>
              <CardContent className="space-y-3">
                {recentAchievements.length > 0 ? (
                  recentAchievements.map((achievement) => (
                    <div key={achievement.id} className="flex items-center space-x-3">
                      <div className="p-2 rounded-lg bg-primary text-primary-foreground">
                        <Trophy className="h-4 w-4" />
                      </div>
                      <div className="flex-1">
                        <p className="font-medium text-foreground">
                          {achievement.title}
                        </p>
                        <Badge variant="secondary" className="text-xs">
                          Earned
                        </Badge>
                      </div>
                      <CheckCircle className="h-5 w-5 text-accent" />
                    </div>
                  ))
                ) : (
                  <div className="text-center py-4">
                    <Trophy className="h-8 w-8 text-muted-foreground mx-auto mb-2" />
                    <p className="text-sm text-muted-foreground">No achievements yet</p>
                    <p className="text-xs text-muted-foreground">Complete courses to earn badges!</p>
                  </div>
                )}
              </CardContent>
            </Card>

            {/* Learning Goal */}
            <Card className="bg-gradient-primary border-0 text-primary-foreground">
              <CardHeader>
                <CardTitle>Weekly Goal</CardTitle>
              </CardHeader>
              <CardContent>
                <div className="space-y-3">
                  <div className="flex justify-between text-sm">
                    <span>5 hours learning</span>
                    <span>3.5/5 hours</span>
                  </div>
                  <Progress value={70} className="bg-primary-foreground/20" />
                  <p className="text-sm opacity-90">
                    You're doing great! 1.5 hours to go.
                  </p>
                </div>
              </CardContent>
            </Card>
          </div>
        </div>
      </main>
    </div>
  );
};

export default Dashboard;
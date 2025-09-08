import Navigation from "@/components/Navigation";
import { useParams, Link } from "react-router-dom";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { BookOpen, Clock, Users, Loader2, Play } from "lucide-react";
import { useQuery } from "@tanstack/react-query";
import { coursesAPI, enrollmentsAPI } from "@/lib/api-services";
import { getCurrentUser } from "@/lib/auth";

const CourseDetail = () => {
  const { id } = useParams();
  const currentUser = getCurrentUser();
  const courseId = id ? parseInt(id) : 0;

  // Fetch course details
  const { data: course, isLoading, error } = useQuery({
    queryKey: ['course', courseId],
    queryFn: () => coursesAPI.getById(courseId),
    enabled: !!courseId,
  });

  // Check if user is enrolled
  const { data: enrollment } = useQuery({
    queryKey: ['enrollment', courseId],
    queryFn: () => enrollmentsAPI.getMyCourse(courseId),
    enabled: !!courseId && !!currentUser,
  });

  if (isLoading) {
    return (
      <div className="min-h-screen bg-background">
        <Navigation />
        <main className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
          <div className="flex items-center justify-center h-64">
            <Loader2 className="h-8 w-8 animate-spin" />
          </div>
        </main>
      </div>
    );
  }

  if (error || !course) {
    return (
      <div className="min-h-screen bg-background">
        <Navigation />
        <main className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
          <div className="text-center">
            <h1 className="text-2xl font-bold text-destructive">Course not found</h1>
            <p className="text-muted-foreground">The course you're looking for doesn't exist.</p>
            <Link to="/courses">
              <Button className="mt-4">Browse Courses</Button>
            </Link>
          </div>
        </main>
      </div>
    );
  }

  const levelColors = {
    BEGINNER: "bg-accent text-accent-foreground",
    INTERMEDIATE: "bg-primary text-primary-foreground",
    ADVANCED: "bg-destructive text-destructive-foreground",
  };

  const levelLabels = {
    BEGINNER: "Beginner",
    INTERMEDIATE: "Intermediate", 
    ADVANCED: "Advanced",
  };

  return (
    <div className="min-h-screen bg-background">
      <Navigation />
      <main className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
        <div className="grid grid-cols-1 lg:grid-cols-3 gap-8">
          {/* Main Content */}
          <div className="lg:col-span-2 space-y-6">
            <Card className="bg-gradient-card border-0">
              <CardHeader>
                <div className="flex items-center justify-between mb-4">
                  <Badge variant="secondary">{course.category}</Badge>
                  <Badge className={levelColors[course.level]}>
                    {levelLabels[course.level]}
                  </Badge>
                </div>
                <CardTitle className="text-3xl">{course.title}</CardTitle>
                <p className="text-muted-foreground text-lg">{course.description}</p>
              </CardHeader>
              <CardContent className="space-y-6">
                <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                  <div className="flex items-center space-x-2">
                    <BookOpen className="h-5 w-5 text-primary" />
                    <div>
                      <p className="text-sm text-muted-foreground">Instructor</p>
                      <p className="font-medium">{course.instructorName}</p>
                    </div>
                  </div>
                  <div className="flex items-center space-x-2">
                    <Clock className="h-5 w-5 text-primary" />
                    <div>
                      <p className="text-sm text-muted-foreground">Duration</p>
                      <p className="font-medium">{course.duration} hours</p>
                    </div>
                  </div>
                  <div className="flex items-center space-x-2">
                    <BookOpen className="h-5 w-5 text-primary" />
                    <div>
                      <p className="text-sm text-muted-foreground">Lessons</p>
                      <p className="font-medium">{course.lessons}</p>
                    </div>
                  </div>
                  <div className="flex items-center space-x-2">
                    <Users className="h-5 w-5 text-primary" />
                    <div>
                      <p className="text-sm text-muted-foreground">Students</p>
                      <p className="font-medium">{course.studentsEnrolled}</p>
                    </div>
                  </div>
                </div>
              </CardContent>
            </Card>

            {/* Course Content */}
            <Card className="bg-gradient-card border-0">
              <CardHeader>
                <CardTitle>What you'll learn</CardTitle>
              </CardHeader>
              <CardContent>
                <ul className="space-y-2">
                  <li className="flex items-center space-x-2">
                    <div className="w-2 h-2 bg-primary rounded-full"></div>
                    <span>Master the fundamentals of {course.category.toLowerCase()}</span>
                  </li>
                  <li className="flex items-center space-x-2">
                    <div className="w-2 h-2 bg-primary rounded-full"></div>
                    <span>Build real-world projects and applications</span>
                  </li>
                  <li className="flex items-center space-x-2">
                    <div className="w-2 h-2 bg-primary rounded-full"></div>
                    <span>Learn from industry best practices</span>
                  </li>
                  <li className="flex items-center space-x-2">
                    <div className="w-2 h-2 bg-primary rounded-full"></div>
                    <span>Get hands-on experience with practical exercises</span>
                  </li>
                </ul>
              </CardContent>
            </Card>
          </div>

          {/* Sidebar */}
          <div className="space-y-6">
            <Card className="bg-gradient-card border-0 sticky top-24">
              <CardHeader>
                <CardTitle>Course Details</CardTitle>
              </CardHeader>
              <CardContent className="space-y-4">
                <div className="space-y-2">
                  <p className="text-sm text-muted-foreground">Category</p>
                  <p className="font-medium">{course.category}</p>
                </div>
                <div className="space-y-2">
                  <p className="text-sm text-muted-foreground">Level</p>
                  <p className="font-medium">{levelLabels[course.level]}</p>
                </div>
                <div className="space-y-2">
                  <p className="text-sm text-muted-foreground">Duration</p>
                  <p className="font-medium">{course.duration} hours</p>
                </div>
                <div className="space-y-2">
                  <p className="text-sm text-muted-foreground">Lessons</p>
                  <p className="font-medium">{course.lessons}</p>
                </div>
                <div className="space-y-2">
                  <p className="text-sm text-muted-foreground">Students Enrolled</p>
                  <p className="font-medium">{course.studentsEnrolled}</p>
                </div>
                
                <div className="pt-4 border-t">
                  {enrollment ? (
                    <div className="space-y-4">
                      <div className="text-center">
                        <p className="text-sm text-muted-foreground">Your Progress</p>
                        <p className="text-2xl font-bold text-primary">{enrollment.progress}%</p>
                      </div>
                      <Link to={`/course/${courseId}/enroll`}>
                        <Button className="w-full bg-gradient-primary">
                          <Play className="h-4 w-4 mr-2" />
                          {enrollment.completionStatus === 'COMPLETED' ? 'Review Course' : 'Continue Learning'}
                        </Button>
                      </Link>
                    </div>
                  ) : (
                    <Link to={`/course/${courseId}/enroll`}>
                      <Button className="w-full bg-gradient-primary">
                        Enroll Now
                      </Button>
                    </Link>
                  )}
                </div>
              </CardContent>
            </Card>
          </div>
        </div>
      </main>
    </div>
  );
};

export default CourseDetail;




import Navigation from "@/components/Navigation";
import { useParams, Link, useNavigate } from "react-router-dom";
import { Card, CardContent, CardFooter, CardHeader, CardTitle } from "@/components/ui/card";
import { Button } from "@/components/ui/button";
import { useToast } from "@/hooks/use-toast";
import { useQuery, useMutation, useQueryClient } from "@tanstack/react-query";
import { coursesAPI, enrollmentsAPI } from "@/lib/api-services";
import { Loader2, BookOpen, Clock, Users } from "lucide-react";

const Enroll = () => {
  const { id } = useParams();
  const { toast } = useToast();
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  const courseId = id ? parseInt(id) : 0;

  // Fetch course details
  const { data: course, isLoading: courseLoading, error: courseError } = useQuery({
    queryKey: ['course', courseId],
    queryFn: () => coursesAPI.getById(courseId),
    enabled: !!courseId,
  });

  // Check if user is already enrolled
  const { data: enrollment, isLoading: enrollmentLoading } = useQuery({
    queryKey: ['enrollment', courseId],
    queryFn: () => enrollmentsAPI.getMyCourse(courseId),
    enabled: !!courseId,
  });

  // Enroll mutation
  const enrollMutation = useMutation({
    mutationFn: () => enrollmentsAPI.enroll(courseId),
    onSuccess: () => {
      toast({ title: "Enrolled", description: "You're enrolled. Enjoy learning!" });
      queryClient.invalidateQueries({ queryKey: ['enrollment', courseId] });
      queryClient.invalidateQueries({ queryKey: ['enrollments'] });
      queryClient.invalidateQueries({ queryKey: ['dashboard'] });
      navigate(`/course/${courseId}`);
    },
    onError: (error: any) => {
      toast({ 
        title: "Enrollment failed", 
        description: error.response?.data || "Please try again" 
      });
    },
  });

  if (courseLoading || enrollmentLoading) {
    return (
      <div className="min-h-screen bg-background">
        <Navigation />
        <main className="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
          <div className="flex items-center justify-center h-64">
            <Loader2 className="h-8 w-8 animate-spin" />
          </div>
        </main>
      </div>
    );
  }

  if (courseError || !course) {
    return (
      <div className="min-h-screen bg-background">
        <Navigation />
        <main className="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
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

  if (enrollment) {
    return (
      <div className="min-h-screen bg-background">
        <Navigation />
        <main className="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
          <Card className="bg-card border border-border shadow-card">
            <CardHeader>
              <CardTitle>Already Enrolled</CardTitle>
            </CardHeader>
            <CardContent className="space-y-4">
              <p className="text-muted-foreground">
                You're already enrolled in this course. Your progress: {enrollment.progress}%
              </p>
              <div className="grid grid-cols-2 gap-4 text-sm">
                <div className="flex items-center space-x-2">
                  <BookOpen className="h-4 w-4 text-primary" />
                  <span>{course.title}</span>
                </div>
                <div className="flex items-center space-x-2">
                  <Clock className="h-4 w-4 text-primary" />
                  <span>{course.duration} hours</span>
                </div>
              </div>
            </CardContent>
            <CardFooter className="space-x-2">
              <Link to={`/course/${courseId}`}>
                <Button variant="outline">Back to Course</Button>
              </Link>
              <Link to="/dashboard">
                <Button>Go to Dashboard</Button>
              </Link>
            </CardFooter>
          </Card>
        </main>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-background">
      <Navigation />
      <main className="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
        <Card className="bg-card border border-border shadow-card">
          <CardHeader>
            <CardTitle>Enroll in {course.title}</CardTitle>
          </CardHeader>
          <CardContent className="space-y-4">
            <p className="text-muted-foreground">
              Confirm your enrollment in this course. You'll have access to all course materials and can track your progress.
            </p>
            
            <div className="grid grid-cols-2 gap-4 text-sm">
              <div className="flex items-center space-x-2">
                <BookOpen className="h-4 w-4 text-primary" />
                <span>{course.lessons} lessons</span>
              </div>
              <div className="flex items-center space-x-2">
                <Clock className="h-4 w-4 text-primary" />
                <span>{course.duration} hours</span>
              </div>
              <div className="flex items-center space-x-2">
                <Users className="h-4 w-4 text-primary" />
                <span>{course.studentsEnrolled} students</span>
              </div>
              <div className="flex items-center space-x-2">
                <BookOpen className="h-4 w-4 text-primary" />
                <span>{course.instructorName}</span>
              </div>
            </div>

            <div className="bg-muted p-4 rounded-lg">
              <h4 className="font-medium mb-2">What you'll get:</h4>
              <ul className="text-sm text-muted-foreground space-y-1">
                <li>• Access to all course materials</li>
                <li>• Progress tracking and achievements</li>
                <li>• Certificate of completion</li>
                <li>• Community support</li>
              </ul>
            </div>
          </CardContent>
          <CardFooter className="space-x-2">
            <Link to={`/course/${courseId}`}>
              <Button variant="outline">Back</Button>
            </Link>
            <Button 
              onClick={() => enrollMutation.mutate()}
              disabled={enrollMutation.isPending}
            >
              {enrollMutation.isPending ? (
                <>
                  <Loader2 className="h-4 w-4 mr-2 animate-spin" />
                  Enrolling...
                </>
              ) : (
                "Confirm Enrollment"
              )}
            </Button>
          </CardFooter>
        </Card>
      </main>
    </div>
  );
};

export default Enroll;




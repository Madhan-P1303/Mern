import { Clock, Users, Star, BookOpen } from "lucide-react";
import { Button } from "@/components/ui/button";
import { Badge } from "@/components/ui/badge";
import { Card, CardContent, CardFooter, CardHeader } from "@/components/ui/card";
import { Link } from "react-router-dom";

interface CourseCardProps {
  id: number;
  title: string;
  description: string;
  instructorName: string;
  duration: number;
  studentsEnrolled: number;
  category: string;
  level: "BEGINNER" | "INTERMEDIATE" | "ADVANCED";
  lessons: number;
}

const CourseCard = ({
  id,
  title,
  description,
  instructorName,
  duration,
  studentsEnrolled,
  category,
  level,
  lessons,
}: CourseCardProps) => {
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
    <Card className="group hover:shadow-elegant transition-all duration-300 hover:-translate-y-1 bg-gradient-card border-0 overflow-hidden">
      <div className="aspect-video bg-muted overflow-hidden flex items-center justify-center">
        <BookOpen className="h-16 w-16 text-muted-foreground" />
      </div>
      
      <CardHeader className="space-y-2">
        <div className="flex items-center justify-between">
          <Badge variant="secondary" className="text-xs">
            {category}
          </Badge>
          <Badge className={`text-xs ${levelColors[level]}`}>
            {levelLabels[level]}
          </Badge>
        </div>
        <h3 className="font-semibold text-lg leading-tight line-clamp-2">
          {title}
        </h3>
        <p className="text-muted-foreground text-sm line-clamp-2">
          {description}
        </p>
      </CardHeader>

      <CardContent className="space-y-3">
        <div className="flex items-center text-sm text-muted-foreground">
          <BookOpen className="h-4 w-4 mr-2" />
          {instructorName}
        </div>
        
        <div className="flex items-center justify-between text-sm text-muted-foreground">
          <div className="flex items-center">
            <Clock className="h-4 w-4 mr-1" />
            {duration} hours
          </div>
            <div className="flex items-center">
              <Users className="h-4 w-4 mr-1" />
              {typeof studentsEnrolled === "number" && !isNaN(studentsEnrolled)
                ? studentsEnrolled.toLocaleString()
                : "N/A"}
            </div>
        </div>

        <div className="flex items-center justify-between">
          <div className="flex items-center">
            <BookOpen className="h-4 w-4 text-primary mr-1" />
            <span className="text-sm font-medium">{lessons} lessons</span>
          </div>
          <div className="text-right">
            <span className="text-sm text-muted-foreground">Free</span>
          </div>
        </div>
      </CardContent>

      <CardFooter className="pt-0">
        <Link to={`/course/${id}`} className="w-full">
          <Button className="w-full bg-gradient-primary hover:opacity-90 transition-opacity">
            Enroll Now
          </Button>
        </Link>
      </CardFooter>
    </Card>
  );
};

export default CourseCard;
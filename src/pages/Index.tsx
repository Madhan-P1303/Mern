import Navigation from "@/components/Navigation";
import CourseCard from "@/components/CourseCard";
import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { 
  BookOpen, 
  Users, 
  Trophy, 
  Clock, 
  CheckCircle, 
  Star,
  ArrowRight,
  Play,
  Target,
  Zap,
  Code,
  Palette,
  Database,
  Megaphone,
  Briefcase,
  Camera,
  Music as MusicIcon,
  Globe,
  Server,
  Shield,
  Languages,
  Award,
  Laptop,
  Smartphone,
  MessageSquare,
  Download
} from "lucide-react";
import { Link } from "react-router-dom";
import heroImage from "@/assets/hero-education.jpg";
import HeroBackground from "@/components/HeroBackground";

const Index = () => {
  const featuredCourses = [
    {
      id: "1",
      title: "Complete Web Development Bootcamp",
      description: "Learn HTML, CSS, JavaScript, React, Node.js and become a full-stack developer",
      instructor: "Sarah Johnson",
      duration: "12 weeks",
      students: 15420,
      rating: 4.8,
      price: 99,
      image: "https://images.unsplash.com/photo-1518770660439-4636190af475?q=80&w=1200&auto=format&fit=crop",
      category: "Programming",
      level: "Beginner" as const,
    },
    {
      id: "2",
      title: "Advanced React & Redux",
      description: "Master advanced React patterns, Redux, and modern state management",
      instructor: "Mike Chen",
      duration: "8 weeks",
      students: 8750,
      rating: 4.9,
      price: 149,
      image: "https://images.unsplash.com/photo-1515879218367-8466d910aaa4?q=80&w=1200&auto=format&fit=crop",
      category: "Programming",
      level: "Advanced" as const,
    },
    {
      id: "3",
      title: "UI/UX Design Fundamentals",
      description: "Learn design principles, user research, wireframing, and prototyping",
      instructor: "Emily Davis",
      duration: "10 weeks",
      students: 12300,
      rating: 4.7,
      price: 89,
      image: "https://images.unsplash.com/photo-1559028012-481c04fa702d?q=80&w=1200&auto=format&fit=crop",
      category: "Design",
      level: "Beginner" as const,
    },
    {
      id: "4",
      title: "Data Science with Python",
      description: "Master data analysis and machine learning with Pandas and scikit-learn",
      instructor: "Dr. James Wilson",
      duration: "16 weeks",
      students: 9200,
      rating: 4.6,
      price: 199,
      image: "https://images.unsplash.com/photo-1519389950473-47ba0277781c?q=80&w=1200&auto=format&fit=crop",
      category: "Data Science",
      level: "Intermediate" as const,
    },
    {
      id: "5",
      title: "Digital Marketing Mastery",
      description: "Learn SEO, social media marketing, content strategy, and analytics",
      instructor: "Lisa Rodriguez",
      duration: "6 weeks",
      students: 18900,
      rating: 4.5,
      price: 79,
      image: "https://images.unsplash.com/photo-1557804506-669a67965ba0?q=80&w=1200&auto=format&fit=crop",
      category: "Marketing",
      level: "Beginner" as const,
    },
  ];

  const stats = [
    { label: "Active Students", value: "50,000+", icon: Users },
    { label: "Expert Instructors", value: "1,200+", icon: BookOpen },
    { label: "Course Hours", value: "10,000+", icon: Clock },
    { label: "Success Stories", value: "25,000+", icon: Trophy },
  ];

  const features = [
    {
      title: "Expert-Led Courses",
      description: "Learn from industry professionals with real-world experience",
      icon: Target,
    },
    {
      title: "Interactive Learning",
      description: "Hands-on projects and practical exercises for better retention",
      icon: Play,
    },
    {
      title: "Flexible Schedule",
      description: "Study at your own pace with lifetime access to courses",
      icon: Clock,
    },
    {
      title: "Career Support",
      description: "Get job placement assistance and career guidance",
      icon: Zap,
    },
    {
      title: "Verified Certificates",
      description: "Earn employer-recognized certificates upon completion",
      icon: Award,
    },
    {
      title: "Vibrant Community",
      description: "Connect with peers and mentors in discussion forums",
      icon: MessageSquare,
    },
    {
      title: "Learn Anywhere",
      description: "Optimized for laptop and mobile learning on the go",
      icon: Laptop,
    },
    {
      title: "Offline Resources",
      description: "Downloadables, templates, and cheat sheets included",
      icon: Download,
    },
  ];

  const categories = [
    { name: "Programming", icon: Code },
    { name: "Design", icon: Palette },
    { name: "Data Science", icon: Database },
    { name: "Marketing", icon: Megaphone },
    { name: "Business", icon: Briefcase },
    { name: "DevOps", icon: Server },
    { name: "Cybersecurity", icon: Shield },
    { name: "Photography", icon: Camera },
    { name: "Music", icon: MusicIcon },
    { name: "Language", icon: Languages },
    { name: "Global Skills", icon: Globe },
  ];

  return (
    <div className="min-h-screen bg-background">
      <Navigation />
      
      {/* Hero Section */}
      <section className="relative overflow-hidden bg-gradient-hero">
        <HeroBackground
          items={[
            { type: "image", src: "https://images.unsplash.com/photo-1524995997946-a1c2e315a42f?q=80&w=1600&auto=format&fit=crop" },
            { type: "image", src: "https://images.unsplash.com/photo-1523580846011-d3a5bc25702b?q=80&w=1600&auto=format&fit=crop" },
            { type: "video", src: "https://cdn.coverr.co/videos/coverr-coding-on-laptop-4012/1080p.mp4", poster: heroImage },
          ]}
          intervalMs={7000}
        />
        
        <div className="relative max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-24 lg:py-32">
          <div className="grid grid-cols-1 lg:grid-cols-2 gap-12 items-center">
            <div className="text-white drop-shadow-[0_2px_6px_rgba(0,0,0,0.6)]">
              <Badge variant="secondary" className="mb-4 bg-white/20 text-white border-white/30">
                🚀 New courses every week
              </Badge>
              
              <h1 className="text-4xl md:text-5xl lg:text-6xl font-bold leading-tight mb-6">
                Transform Your
                <span className="block bg-gradient-to-r from-yellow-400 to-orange-400 bg-clip-text text-transparent">
                  Career Today
                </span>
              </h1>
              
              <p className="text-xl md:text-2xl mb-8 text-white/90 leading-relaxed">
                Join thousands of students learning in-demand skills from world-class instructors. 
                Start your journey to success with our comprehensive courses.
              </p>
              
              <div className="flex flex-col sm:flex-row gap-4 mb-8">
                <Link to="/courses">
                  <Button size="lg" className="bg-white text-primary hover:bg-white/90 font-semibold px-8">
                    Start Learning
                    <ArrowRight className="ml-2 h-5 w-5" />
                  </Button>
                </Link>
                
                <Link to="/demo">
                  <Button 
                    size="lg" 
                    variant="outline" 
                    className="border-white text-white bg-white/20 hover:bg-white hover:text-primary font-semibold px-8"
                  >
                    <Play className="mr-2 h-5 w-5" />
                    Watch Demo
                  </Button>
                </Link>
              </div>
              
              <div className="flex items-center space-x-6">
                <div className="flex items-center">
                  <Star className="h-5 w-5 text-yellow-400 fill-current" />
                  <span className="ml-1 font-semibold">4.9/5</span>
                </div>
                <div className="text-white/80">from 50,000+ reviews</div>
              </div>
            </div>
            
            <div className="relative">
              <div className="bg-white/15 backdrop-blur-md rounded-2xl p-8 border border-white/25 shadow-2xl">
                <h3 className="text-white text-xl font-semibold mb-6">Start your free trial today</h3>
                <ul className="space-y-4 text-white/90">
                  {[
                    "Access to all premium courses",
                    "Interactive coding exercises", 
                    "24/7 community support",
                    "Certificate of completion"
                  ].map((feature, index) => (
                    <li key={index} className="flex items-center">
                      <CheckCircle className="h-5 w-5 text-accent mr-3" />
                      {feature}
                    </li>
                  ))}
                </ul>
                <Button className="w-full mt-6 bg-accent hover:bg-accent/90 text-white font-semibold">
                  Get Started Free
                </Button>
              </div>
            </div>
          </div>
        </div>
      </section>

      {/* Stats Section */}
      <section className="py-16 bg-muted/30">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="grid grid-cols-2 lg:grid-cols-4 gap-8">
            {stats.map((stat, index) => (
              <div key={index} className="text-center">
                <div className="bg-gradient-primary rounded-full p-4 w-16 h-16 mx-auto mb-4 flex items-center justify-center">
                  <stat.icon className="h-8 w-8 text-primary-foreground" />
                </div>
                <div className="text-3xl font-bold text-primary mb-2">{stat.value}</div>
                <div className="text-muted-foreground">{stat.label}</div>
              </div>
            ))}
          </div>
        </div>
      </section>

      {/* Categories Section */}
      <section className="py-20">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-16">
            <h2 className="text-3xl md:text-4xl font-bold mb-4">
              Browse by Category
            </h2>
            <p className="text-xl text-muted-foreground max-w-2xl mx-auto">
              Explore a wide range of topics to match your interests and goals
            </p>
          </div>

          <div className="grid grid-cols-2 md:grid-cols-3 lg:grid-cols-6 gap-4">
            {categories.map((cat, index) => (
              <Card key={index} className="text-center bg-gradient-card border-0 shadow-card hover:shadow-elegant transition-all duration-300">
                <CardHeader>
                  <div className="bg-gradient-primary rounded-full p-3 w-12 h-12 mx-auto mb-3 flex items-center justify-center">
                    <cat.icon className="h-6 w-6 text-primary-foreground" />
                  </div>
                  <CardTitle className="text-base">{cat.name}</CardTitle>
                </CardHeader>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* Features Section */}
      <section className="py-20">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-16">
            <h2 className="text-3xl md:text-4xl font-bold mb-4">
              Why Choose EduQuest?
            </h2>
            <p className="text-xl text-muted-foreground max-w-2xl mx-auto">
              We provide the tools, resources, and support you need to succeed in your learning journey
            </p>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-8">
            {features.map((feature, index) => (
              <Card key={index} className="text-center bg-gradient-card border-0 shadow-card hover:shadow-elegant transition-all duration-300">
                <CardHeader>
                  <div className="bg-gradient-primary rounded-full p-3 w-14 h-14 mx-auto mb-4 flex items-center justify-center">
                    <feature.icon className="h-7 w-7 text-primary-foreground" />
                  </div>
                  <CardTitle className="text-lg">{feature.title}</CardTitle>
                </CardHeader>
                <CardContent>
                  <p className="text-muted-foreground">{feature.description}</p>
                </CardContent>
              </Card>
            ))}
          </div>
        </div>
      </section>

      {/* Featured Courses */}
      <section className="py-20 bg-muted/30">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center mb-16">
            <h2 className="text-3xl md:text-4xl font-bold mb-4">
              Featured Courses
            </h2>
            <p className="text-xl text-muted-foreground max-w-2xl mx-auto">
              Start with our most popular courses, taught by industry experts
            </p>
          </div>
          
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-8 mb-12">
            {featuredCourses.map((course) => (
              <CourseCard key={course.id} {...course} />
            ))}
          </div>
          
          <div className="text-center">
            <Link to="/courses">
              <Button size="lg" variant="outline" className="font-semibold">
                View All Courses
                <ArrowRight className="ml-2 h-5 w-5" />
              </Button>
            </Link>
          </div>
        </div>
      </section>

      {/* CTA Section */}
      <section className="py-20 bg-gradient-primary">
        <div className="max-w-4xl mx-auto px-4 sm:px-6 lg:px-8 text-center text-primary-foreground">
          <h2 className="text-3xl md:text-4xl font-bold mb-6">
            Ready to Start Your Learning Journey?
          </h2>
          <p className="text-xl mb-8 opacity-90">
            Join thousands of students who have transformed their careers with EduQuest
          </p>
          <div className="flex flex-col sm:flex-row gap-4 justify-center">
            <Link to="/courses">
              <Button size="lg" variant="secondary" className="font-semibold px-8">
                Browse Courses
              </Button>
            </Link>
            <Link to="/dashboard">
              <Button 
                size="lg" 
                className="bg-primary-foreground text-primary hover:bg-primary-foreground/90 font-semibold px-8"
              >
                View Dashboard
              </Button>
            </Link>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="bg-card border-t border-border py-12">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="text-center">
            <div className="flex items-center justify-center space-x-2 mb-4">
              <div className="bg-gradient-primary rounded-lg p-2">
                <BookOpen className="h-6 w-6 text-primary-foreground" />
              </div>
              <span className="text-2xl font-bold text-foreground">EduQuest</span>
            </div>
            <p className="text-muted-foreground mb-4">
              Empowering learners worldwide with quality education
            </p>
            <p className="text-sm text-muted-foreground">
              © 2024 EduQuest. All rights reserved.
            </p>
          </div>
        </div>
      </footer>
    </div>
  );
};

export default Index;
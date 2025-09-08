import Navigation from "@/components/Navigation";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Users, Target, BookOpen, Award, HeartHandshake } from "lucide-react";

const About = () => {
  const values = [
    { icon: Users, title: "Learner-First", desc: "We obsess over outcomes that matter to learners and their careers." },
    { icon: Target, title: "Quality", desc: "Every course is carefully curated and reviewed by industry experts." },
    { icon: Award, title: "Credibility", desc: "Certificates that showcase verified, job-ready skills." },
    { icon: HeartHandshake, title: "Community", desc: "Supportive mentors and peers to help you stay motivated." },
  ];

  return (
    <div className="min-h-screen bg-background">
      <Navigation />
      <main className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <div className="text-center mb-12">
          <h1 className="text-4xl font-bold mb-3">About EduQuest</h1>
          <p className="text-lg text-muted-foreground max-w-3xl mx-auto">
            EduQuest is your companion for mastering in-demand skills. We bring together expert-led
            content, hands-on projects, and a vibrant community to help you achieve your learning goals.
          </p>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6 mb-12">
          <Card className="bg-gradient-card border-0">
            <CardHeader>
              <CardTitle>Our Mission</CardTitle>
            </CardHeader>
            <CardContent className="text-muted-foreground">
              Empower learners worldwide with accessible, high-quality education that leads to real career growth.
            </CardContent>
          </Card>
          <Card className="bg-gradient-card border-0">
            <CardHeader>
              <CardTitle>What We Offer</CardTitle>
            </CardHeader>
            <CardContent className="text-muted-foreground">
              Curated courses, practical exercises, projects, certificates, and career guidance—all in one place.
            </CardContent>
          </Card>
        </div>

        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6">
          {values.map((v, i) => (
            <Card key={i} className="text-center bg-gradient-card border-0">
              <CardHeader>
                <div className="bg-gradient-primary rounded-full p-3 w-14 h-14 mx-auto mb-4 flex items-center justify-center">
                  <v.icon className="h-7 w-7 text-primary-foreground" />
                </div>
                <CardTitle className="text-lg">{v.title}</CardTitle>
              </CardHeader>
              <CardContent className="text-muted-foreground">{v.desc}</CardContent>
            </Card>
          ))}
        </div>

        <div className="mt-12 text-center">
          <div className="inline-flex items-center space-x-3">
            <BookOpen className="h-6 w-6 text-primary" />
            <p className="text-muted-foreground">Start your EduQuest today.</p>
          </div>
        </div>
      </main>
    </div>
  );
};

export default About;




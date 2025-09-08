import Navigation from "@/components/Navigation";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import heroImage from "@/assets/hero-education.jpg";

const Demo = () => {
  // Default to a high-quality, embeddable video (Big Buck Bunny) if not provided
  const ytId = (import.meta.env.VITE_DEMO_VIDEO_YT_ID as string | undefined) || "aqz-KE-bpKQ";

  return (
    <div className="min-h-screen bg-background">
      <Navigation />
      <main className="max-w-5xl mx-auto px-4 sm:px-6 lg:px-8 py-10">
        <Card className="bg-card border border-border shadow-card overflow-hidden">
          <CardHeader>
            <CardTitle>EduQuest Product Demo</CardTitle>
          </CardHeader>
          <CardContent>
            <div className="aspect-video w-full rounded-lg overflow-hidden bg-black">
              <iframe
                className="w-full h-full"
                src={`https://www.youtube-nocookie.com/embed/${ytId}?rel=0&modestbranding=1&color=white`}
                title="EduQuest Demo"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture; web-share"
                referrerPolicy="strict-origin-when-cross-origin"
                allowFullScreen
              />
            </div>
            <p className="text-sm text-muted-foreground mt-3">
              Tip: Set <code>VITE_DEMO_VIDEO_YT_ID</code> to your YouTube video ID for a branded demo. The page currently uses a high-quality default fallback.
            </p>
          </CardContent>
        </Card>
      </main>
    </div>
  );
};

export default Demo;



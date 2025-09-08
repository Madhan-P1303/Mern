import Navigation from "@/components/Navigation";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { useToast } from "@/hooks/use-toast";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import { loginUser } from "@/lib/auth";
import { useNavigate } from "react-router-dom";

const schema = z.object({ email: z.string().email(), password: z.string().min(1, "Password is required") });
type FormData = z.infer<typeof schema>;

const Login = () => {
  const { toast } = useToast();
  const navigate = useNavigate();
  const form = useForm<FormData>({ resolver: zodResolver(schema), defaultValues: { email: "", password: "" } });

  const onSubmit = async (data: FormData) => {
    try {
      await loginUser(data.email, data.password);
      toast({ title: "Logged in", description: "Welcome back to EduQuest!" });
      navigate("/dashboard");
    } catch (err: any) {
      toast({ title: "Login failed", description: err.message ?? "Please try again" });
    }
  };

  return (
    <div className="min-h-screen bg-background">
      <Navigation />
      <main className="max-w-md mx-auto px-4 sm:px-6 lg:px-8 py-12">
        <Card className="bg-gradient-card border-0">
          <CardHeader>
            <CardTitle>Log in to EduQuest</CardTitle>
          </CardHeader>
          <CardContent>
            <form className="space-y-4" onSubmit={form.handleSubmit(onSubmit)}>
              <div>
                <label className="block text-sm mb-1">Email</label>
                <Input type="email" {...form.register("email")} />
                {form.formState.errors.email && (
                  <p className="text-sm text-destructive mt-1">{form.formState.errors.email.message}</p>
                )}
              </div>
              <div>
                <label className="block text-sm mb-1">Password</label>
                <Input type="password" {...form.register("password")} />
                {form.formState.errors.password && (
                  <p className="text-sm text-destructive mt-1">{form.formState.errors.password.message}</p>
                )}
              </div>
              <Button type="submit" className="w-full" disabled={form.formState.isSubmitting}>
                {form.formState.isSubmitting ? "Signing in..." : "Login"}
              </Button>
            </form>
          </CardContent>
        </Card>
      </main>
    </div>
  );
};

export default Login;



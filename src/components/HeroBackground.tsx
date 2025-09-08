import { useEffect, useMemo, useState } from "react";

type MediaItem = {
  type: "image" | "video";
  src: string;
  poster?: string;
};

interface HeroBackgroundProps {
  items: MediaItem[];
  intervalMs?: number;
}

const HeroBackground = ({ items, intervalMs = 6000 }: HeroBackgroundProps) => {
  const safeItems = useMemo(() => (items?.length ? items : []), [items]);
  const [index, setIndex] = useState(0);

  useEffect(() => {
    if (safeItems.length <= 1) return;
    const id = setInterval(() => {
      setIndex((i) => (i + 1) % safeItems.length);
    }, intervalMs);
    return () => clearInterval(id);
  }, [safeItems.length, intervalMs]);

  if (!safeItems.length) return null;

  return (
    <div className="absolute inset-0 overflow-hidden">
      {safeItems.map((item, i) => {
        const isActive = i === index;
        const baseClass =
          "absolute inset-0 transition-opacity duration-700 ease-in-out" +
          (isActive ? " opacity-100" : " opacity-0");

        if (item.type === "video") {
          return (
            <video
              key={`vid-${i}`}
              className={baseClass + " object-cover w-full h-full"}
              src={item.src}
              poster={item.poster}
              autoPlay
              muted
              loop
              playsInline
              preload="auto"
              controls={false}
            />
          );
        }

        return (
          <img
            key={`img-${i}`}
            className={baseClass + " object-cover w-full h-full"}
            src={item.src}
            alt="hero background"
            referrerPolicy="no-referrer"
          />
        );
      })}
      <div className="absolute inset-0 bg-gradient-to-b from-black/60 via-black/40 to-black/20" />
    </div>
  );
};

export default HeroBackground;



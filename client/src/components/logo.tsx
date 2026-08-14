type LogoProps = {
  size?: "large" | "nav"
}

export function Logo({ size = "large" }: LogoProps) {
  const textSize = size === "large" ? "text-7xl" : "text-5xl"

  return (
    <div className="flex items-center justify-center gap-3">
      <div className={`${textSize} font-medium tracking-tight`}>
        <span className="text-blue-500">u</span>
        <span className="text-red-500">r</span>
        <span className="text-yellow-500">l</span>
        <span className="text-blue-500">2</span>
        <span className="text-green-500">P</span>
        <span className="text-red-500">r</span>
        <span className="text-yellow-500">o</span>
        <span className="text-blue-500">d</span>
        <span className="text-green-500">u</span>
        <span className="text-red-500">c</span>
        <span className="text-yellow-500">t</span>
        <span className="text-blue-500"> </span>
        <span className="text-blue-500"> </span>
        <span className="text-blue-500">A</span>
        <span className="text-green-500">I</span>
      </div>
    </div>
  )
}

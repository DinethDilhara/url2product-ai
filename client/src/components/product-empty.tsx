import { HugeiconsIcon } from "@hugeicons/react"
import { SpatulaIcon } from "@hugeicons/core-free-icons"

import {
  Empty,
  EmptyContent,
  EmptyDescription,
  EmptyHeader,
  EmptyMedia,
  EmptyTitle,
} from "@/components/ui/empty"

export function ProductEmpty() {
  return (
    <Empty className="py-20">
      <EmptyHeader>
        <EmptyMedia variant="icon">
          <HugeiconsIcon icon={SpatulaIcon} size={24} />
        </EmptyMedia>

        <EmptyTitle>No product yet</EmptyTitle>

        <EmptyDescription>
          Paste a product URL above and click Scrape to extract its details.
        </EmptyDescription>
      </EmptyHeader>

      <EmptyContent />
    </Empty>
  )
}

# Third-Party Licenses & Attributions — KuaYue 1.0 (NeoForge port)

This mod **does not bundle any third-party source or bytecode** inside its
JAR. All third-party code it relies on is provided at runtime by the game
(Minecraft), the NeoForge mod loader, and the other mods listed below, each
distributed under its own license. This document is an **attribution list**
only.

| Dependency | Role | License |
|---|---|---|
| Minecraft | Base game (runtime) | Minecraft EULA |
| NeoForge (net.neoforged) | Mod loader | LGPL-2.1 |
| Create (com.simibubi.create) | Core automation framework | MIT |
| Ponder (net.createmod.ponder) | In-game documentation (ships with Create) | MIT |
| Flywheel (dev.engine-room.flywheel) | Rendering backend | MIT |
| KasugaLib (kasuga.lib) | Companion library (bundles its own third-party code — see its THIRD-PARTY-LICENSES) | MIT |
| Registrate (com.tterrag.registrate) | Registry DSL | MIT |
| JOML (org.joml) | Math library | MIT (JOML License) |
| LWJGL (org.lwjgl) | Low-level OpenGL/input bindings (via Minecraft) | BSD-3-Clause |
| Mojang libraries (com.mojang:*) | Minecraft runtime libraries | Minecraft EULA / Mojang Terms |

The KuaYue **source code and assets** are licensed under the project
`LICENSE` (MIT) and `LICENSE-RESOURCES` (CC BY-SA 4.0) files respectively.

> Note: KasugaLib — a required dependency of this mod — bundles 27
> third-party libraries (Javalin, Javet+V8, Eclipse Jetty, Kotlin stdlib,
> LWJGL Yoga, MALI). Their full license texts live in KasugaLib's own
> `THIRD-PARTY-LICENSES` file, not here, because that code ships inside
> KasugaLib's JAR rather than KuaYue's.

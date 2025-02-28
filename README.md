# dzCashBlock

dzCashBlock is a Minecraft plugin that enhances gameplay by introducing a money drop system and custom brushes for terrain modification.

## Features

### Money Drops System
- Players earn virtual currency by breaking specific blocks.
- Default configuration includes money drops from stone (configurable).
- Encourages an in-game economy by rewarding mining activities.

### Brushes – Terrain Editing Tools
- Custom, configurable tools for modifying terrain and structures.
- Useful for terraforming and world-building.
- Ideal for administrators and builders.

### Configuration
- Fully configurable via `config.yml` and `messages.yml`.
- Define which blocks drop money and adjust brush settings.

## Installation
1. Download the latest release from the [Releases](https://github.com/dziubekk/dzCashBlock/releases) page.
2. Place the `dzCashBlock.jar` file in your server's `plugins` folder.
3. Restart or reload your server.
4. Configure settings in `config.yml` and `messages.yml` if needed.

## Commands
| Command | Description |
|---------|-------------|
| `/dzcashblock <authors/help/reload>` | Reloads the plugin configuration. |
| `/getbrush <type> <player>` | Gives a brush tool to a player. |
| `/wallet` | Shows player's wallet. |
| `/adminwallet <add/check/set/take> <player> [amount]` | Edits player's wallet. |
| `/fstone <amount>` | Puts money in the target block. |

## Permissions
| Permission | Description |
|------------|-------------|
| `dzcashblock.admin` | Access to admin commands like reload or fstone. |
| `dzcashblock.player` | Access to player commands like wallet. |

## Future Updates
- Additional customization options for money drops.
- More brush tools and effects.
- Compatibility improvements.

## Contributing
Feel free to submit issues or pull requests on [GitHub](https://github.com/dziubekk/dzCashBlock).

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

# Blåhaj for GTNH!

Make your healing axe look like a soft shark friend.

This is a vibecoded (except for this README) adaptation of older lovely mods, modified to work with the healing axe in GTNH.  All credit to them, who actually did the work:
- [Blåhaj](https://github.com/hibiii/Blahaj) (Unlicense / public domain)
- [Blåhaj of Undying](https://codeberg.org/enjarai/blahaj-of-undying) (Unlicense / public domain)
- [Blåhaj Beyond](https://github.com/IThundxr/Blahaj-Beyond) (LGPL-2.0; hence this mod is LGPL-2.1)

This project is not affiliated with GTNH or ExtraUtilities or IKEA or the source projects above or anything or anyone else.

I tested this minimally on 2.9rc2.  YMMV.  Let me know if there's something to fix (an issue here or @mgomezch in Discord).


## Usage

You can craft the plushie item.  More likely, though, you'll want to rename your healing axe in an anvil.

If the name includes "shark", "blahaj", "shonk", or any size/color/pride-flag name (see below), it will change its appearance.

There's a keybind for an in-game menu where you can fine-tune the position and angle of your haj.  You can also make it spin because why not.

Config goes in `config/blahaj.cfg`.

You can also drop a PNG with transparency in `config/blahaj/custom/` and re-enter your world.  The name of image files there will be split at underscores, the extension removed, and then that becomes the words you use to get that image as the appearance.  For example, if you name your image "foo_bar.png" then you can get that image with "Foo bar" or such.


## Variant keywords

These are case-insensitive.  Try naming your healing axe "Smol trans shonk" or such.

| Pride variant      | Keywords                                  |
|:-------------------|:------------------------------------------|
| Transgender pride  | `trans`, `transgender`, `transhaj`        |
| Rainbow pride      | `pride`, `rainbow`, `lgbt`                |
| Progress pride     | `prider`, `progress`, `lgbtqia`, `lgbtq+` |
| Lesbian pride      | `lesbian`, `yuri`                         |
| Gay pride          | `gay`, `homosexual`, `yaoi`, `mlm`        |
| Bisexual pride     | `bi`, `bisexual`                          |
| Pansexual pride    | `pan`, `pansexual`                        |
| Asexual pride      | `ace`, `asexual`                          |
| Aroace pride       | `aroace`                                  |
| Aromantic pride    | `aro`, `aromantic`                        |
| Non-binary pride   | `enby`, `non binary`, `non-binary`, `nb`  |
| Genderfluid pride  | `genderfluid`                             |
| Genderqueer pride  | `genderqueer`                             |
| Agender pride      | `agender`                                 |
| Intersex pride     | `intersex`                                |
| Polysexual pride   | `poly`, `polysexual`                      |
| Demiboy pride      | `demiboy`                                 |
| Demigirl pride     | `demigirl`                                |
| Demisexual pride   | `demisexual`                              |
| Demiromantic pride | `demiromantic`                            |
| Greysexual pride   | `greysexual`, `graysexual`                |
| Greyromantic pride | `greyromantic`, `grayromantic`            |
| Greyrose pride     | `greyrose`, `grayrose`                    |

| Color        | Keywords                                               |
|:-------------|:-------------------------------------------------------|
| Classic blue | `blue`, `blahaj`, `blåhaj`, `shork`, `shark` (default) |
| Grey         | `grey`, `gray`, `grahaj`, `gråhaj`, `klappar haj`      |
| Red          | `red`                                                  |
| Orange       | `orange`                                               |
| Yellow       | `yellow`                                               |
| Lime         | `lime`                                                 |
| Green        | `green`                                                |
| Cyan         | `cyan`                                                 |
| Purple       | `purple`                                               |
| Magenta      | `magenta`                                              |
| Pink         | `pink`, `rosahaj`                                      |
| Black        | `black`                                                |
| White        | `white`                                                |

| Size    | Keywords                                                     | Default scale |
|:--------|:-------------------------------------------------------------|:--------------|
| Chonker | `chonker`, `chonk`, `big`, `large`                           | `1.5x`        |
| Smöl    | `small`, `smol`, `smöl`, `smolhaj`, `tiny`, `mini`, `pocket` | `0.65x`       |
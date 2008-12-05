/*
 * Balie - BAseLine Information Extraction
 * Copyright (C) 2004-2007  David Nadeau
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */
/*
 * File created on 07-02-26
 */
package ca.uottawa.balie;

/**
 * @author David Nadeau (pythonner@gmail.com)
 */
public enum NamedEntityTypeEnum implements NamedEntityTypeEnumI {
    START               (0, null),
    END                 (1, null),
    name                (2,"name"),
    person              (3,"person"),
    first_name          (4,"first_name"),
    person_title        (5,"person_title"),
    last_name           (6,"last_name"),
    celebrity           (7,"celebrity"),
    vocation            (8,"vocation"),
    title               (9,"title"),
    character           (10,"character"),
    organization        (11,"organization"),
    company             (12,"company"),
    company_designator  (121,"company_designator"),
    ticker_symbol       (125,"ticker_symbol"),
    military            (13,"military"),
    association         (14,"association"),
    government          (15,"government"),
    political_party     (16,"political_party"),
    political_line      (17,"political_line"),
    nationality         (18,"nationality"),
    market              (19,"market"),
    religious_group     (20,"religious_group"),
    sports_team         (21,"sports_team"),
    location            (22,"location"),
    geo_political       (23,"geo_political"),
    city                (24,"city"),
    state_province      (25,"state_province"),
    county              (26,"county"),
    country             (27,"country"),
    region              (28,"region"),
    geological          (29,"geological"),
    landform            (30,"landform"),
    waterform           (31,"waterform"),
    river               (32,"river"),
    lake                (33,"lake"),
    sea                 (34,"sea"),
    ocean_bay           (35,"ocean_bay"),
    continent           (36,"continent"),
    astral_body         (37,"astral_body"),
    planet              (38,"planet"),
    star                (39,"star"),
    facility            (40,"facility"),
    property            (41,"property"),
    amphitheatre        (42,"amphitheatre"),
    cathedral           (43,"cathedral"),
    castle              (44,"castle"),
    skyscraper          (45,"skyscraper"),
    sport_place         (46,"sport_place"),
    school              (47,"school"),
    museum              (48,"museum"),
    airport             (49,"airport"),
    port                (50,"port"),
    library             (51,"library"),
    hotel               (52,"hotel"),
    hospital            (53,"hospital"),
    line                (54,"line"),
    road                (55,"road"),
    bridge              (56,"bridge"),
    station             (57,"station"),
    railroad            (58,"railroad"),
    park                (59,"park"),
    amusement_park      (60,"amusement_park"),
    monument            (61,"monument"),
    product             (62,"product"),
    vehicules           (63,"vehicules"),
    car                 (64,"car"),
    ship                (65,"ship"),
    train               (66,"train"),
    aircraft            (67,"aircraft"),
    spaceship           (68,"spaceship"),
    art                 (69,"art"),
    opera_musical       (70,"opera_musical"),
    music               (124,"music"),
    song                (71,"song"),
    painting            (72,"painting"),
    sculpture           (73,"sculpture"),
    media               (74,"media"),
    broadcast           (75,"broadcast"),
    movie               (76,"movie"),
    book                (77,"book"),
    newspaper           (78,"newspaper"),
    magazine            (79,"magazine"),
    blog                (126,"blog"),
    operating_system    (123,"operating_system"),
    weapon              (80,"weapon"),
    food_brand          (81,"food_brand"),
    food                (82,"food"),
    clothes             (83,"clothes"),
    drug                (84,"drug"),
    event               (85,"event"),
    game                (86,"game"),
    holiday             (87,"holiday"),
    war                 (88,"war"),
    hurricane           (89,"hurricane"),
    crime               (90,"crime"),
    conference          (91,"conference"),
    natural_object      (92,"natural_object"),
    living_thing        (93,"living_thing"),
    animal              (94,"animal"),
    invertebrate        (95,"invertebrate"),
    insect              (96,"insect"),
    sea_animal          (97,"sea_animal"),
    vertebrate          (98,"vertebrate"),
    fish                (99,"fish"),
    reptile             (100,"reptile"),
    bird                (101,"bird"),
    mammal              (102,"mammal"),
    vegetal             (103,"vegetal"),
    mineral             (104,"mineral"),
    unit                (105,"unit"),
    measure             (106,"measure"),
    currency            (107,"currency"),
    month               (108,"month"),
    weekday             (109,"weekday"),
    misc                (110,"misc"),
    disease             (111,"disease"),
    god                 (112,"god"),
    religion            (113,"religion"),
    color               (114,"color"),
    language            (115,"language"),
    award               (116,"award"),
    sport               (117,"sport"),
    academic            (118,"academic"),
    rule                (119,"rule"),
    theory              (120,"theory"),
    NOTHING             (122,"nothing");

    private final int bitPos;
    private String label; 
    
    private NamedEntityTypeEnum(int bitPos, String label) {
        this.bitPos = bitPos;
        this.label = label;
    }
    
    public int BitPos()   { return bitPos; }
    public String Label() { return label; }
    public void MapNewLabel(String pi_NewLabel) { label = pi_NewLabel; }

    public static NamedEntityType Enamex() {
        NamedEntityType ne = new NamedEntityType(NamedEntityTypeEnum.values().length, null);
        NamedEntityTypeEnum[] vals = NamedEntityTypeEnum.values();
        for (int i = 0; i != vals.length; ++i) {
            if (vals[i] != NamedEntityTypeEnum.START && 
                    vals[i] != NamedEntityTypeEnum.END) {
                ne.AddType(vals[i], null);
            }
        }
        return ne;
    }

    public static NamedEntityType NaturallyOccursLowercased() {
        NamedEntityType ne = new NamedEntityType(NamedEntityTypeEnum.values().length, null);
        ne.AddType(vocation, null);
        ne.AddType(political_line, null);
        ne.AddType(weapon, null);
        ne.AddType(food, null);
        ne.AddType(insect, null);
        ne.AddType(sea_animal, null);
        ne.AddType(fish, null);
        ne.AddType(reptile, null);
        ne.AddType(bird, null);
        ne.AddType(mammal, null);
        ne.AddType(vegetal, null);
        ne.AddType(mineral, null);
        ne.AddType(measure, null);
        ne.AddType(disease, null);
        ne.AddType(religion, null);
        ne.AddType(color, null);
        ne.AddType(sport, null);
        return ne;
    }    
    
    public static NamedEntityType Mergable() {
        NamedEntityType ne = new NamedEntityType(NamedEntityTypeEnum.values().length, null);
        ne.AddType(first_name, null);
        ne.AddType(person_title, null);
        ne.AddType(last_name, null);
        ne.AddType(first_name, null);
        ne.AddType(celebrity, null);
        ne.AddType(company, null);
        ne.AddType(company_designator, null);
        return ne;
    }
    
    public static NamedEntityType HighlyAmbiguous() {
        NamedEntityType ne = new NamedEntityType(NamedEntityTypeEnum.values().length, null);
        ne.AddType(song, null);
        ne.AddType(book, null);
        ne.AddType(movie, null);
        ne.AddType(broadcast, null);
        ne.AddType(opera_musical, null);
        ne.AddType(ticker_symbol, null);
        return ne;
    }
}

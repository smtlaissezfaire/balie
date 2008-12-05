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
 * File created on 07-03-13
 */
package ca.uottawa.balie;

import ca.uottawa.balie.NamedEntityTypeEnumI;

/**
 * @author David Nadeau (pythonner@gmail.com)
 */
public enum NamedEntityTypeEnumMappingNerf implements NamedEntityTypeEnumI {
    START               (0,null),
    END                 (1,null),
    name                (2,null),
    person              (3,null),
    first_name          (4,"PERSON"),
    person_title        (5,"PERSON"),
    last_name           (6,"PERSON"),
    celebrity           (7,"PERSON"),
    vocation            (8,null),
    title               (9,null),
    character           (10,null),
    organization        (11,null),
    company             (12,"ORGANIZATION"),
    company_designator  (121,"ORGANIZATION"),
    ticker_symbol       (125,null),
    military            (13,"ORGANIZATION"),
    association         (14,"ORGANIZATION"),
    government          (15,"ORGANIZATION"),
    political_party     (16,null),
    political_line      (17,null),
    nationality         (18,null),
    market              (19,null),
    religious_group     (20,null),
    sports_team         (21,null),
    location            (22,null),
    geo_political       (23,null),
    city                (24,"LOCATION"),
    state_province      (25,"LOCATION"),
    county              (26,null),
    country             (27,"LOCATION"),
    region              (28,null),
    geological          (29,null),
    landform            (30,null),
    waterform           (31,null),
    river               (32,null),
    lake                (33,null),
    sea                 (34,null),
    ocean_bay           (35,null),
    continent           (36,null),
    astral_body         (37,null),
    planet              (38,null),
    star                (39,null),
    facility            (40,null),
    property            (41,null),
    amphitheatre        (42,null),
    cathedral           (43,null),
    castle              (44,null),
    skyscraper          (45,null),
    sport_place         (46,null),
    school              (47,null),
    museum              (48,null),
    airport             (49,null),
    port                (50,null),
    library             (51,null),
    hotel               (52,null),
    hospital            (53,null),
    line                (54,null),
    road                (55,null),
    bridge              (56,null),
    station             (57,null),
    railroad            (58,null),
    park                (59,null),
    amusement_park      (60,null),
    monument            (61,null),
    product             (62,null),
    vehicules           (63,null),
    car                 (64,null),
    ship                (65,null),
    train               (66,null),
    aircraft            (67,null),
    spaceship           (68,null),
    art                 (69,null),
    opera_musical       (70,null),
    music               (124,null),
    song                (71,null),
    painting            (72,null),
    sculpture           (73,null),
    media               (74,null),
    broadcast           (75,null),
    movie               (76,null),
    book                (77,null),
    newspaper           (78,null),
    magazine            (79,null),
    blog                (126,null),
    operating_system    (123,null),
    weapon              (80,null),
    food_brand          (81,null),
    food                (82,null),
    clothes             (83,null),
    drug                (84,null),
    event               (85,null),
    game                (86,null),
    holiday             (87,null),
    war                 (88,null),
    hurricane           (89,null),
    crime               (90,null),
    conference          (91,null),
    natural_object      (92,null),
    living_thing        (93,null),
    animal              (94,null),
    invertebrate        (95,null),
    insect              (96,null),
    sea_animal          (97,null),
    vertebrate          (98,null),
    fish                (99,null),
    reptile             (100,null),
    bird                (101,null),
    mammal              (102,null),
    vegetal             (103,null),
    mineral             (104,null),
    unit                (105,null),
    measure             (106,null),
    currency            (107,null),
    month               (108,"MONTH"),
    weekday             (109,null),
    misc                (110,null),
    disease             (111,null),
    god                 (112,null),
    religion            (113,null),
    color               (114,null),
    language            (115,null),
    award               (116,null),
    sport               (117,null),
    academic            (118,null),
    rule                (119,null),
    theory              (120,null),
    NOTHING             (122,"nothing");
    
    private final int bitPos;
    private String label; 
    
    private NamedEntityTypeEnumMappingNerf(int bitPos, String label) {
        this.bitPos = bitPos;
        this.label = label;
    }
    
    public int BitPos()   { return bitPos; }
    public String Label() { return label; }
    public void MapNewLabel(String pi_NewLabel) {throw new Error("Should not map anything on these types");}
}

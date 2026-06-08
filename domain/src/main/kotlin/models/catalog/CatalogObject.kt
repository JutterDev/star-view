package models.catalog

// Name;Type;RA;Dec;Const;MajAx;MinAx;PosAng;B-Mag;V-Mag;J-Mag;H-Mag;K-Mag;SurfBr;Hubble;Pax;Pm-RA;Pm-Dec;RadVel;Redshift;Cstar U-Mag;Cstar B-Mag;Cstar V-Mag;M;NGC;IC;Cstar Names;Identifiers;Common names;NED notes;OpenNGC notes;Sources

/**
 * Объект каталога небесных объектов (на основе OpenNGC)
 */
data class CatalogObject(
    // Базовые параметры (ваши исходные)
    val id: Int,
    val name: String,
    val type: String,
    val superType: ObjectType,

    // Геометрия и положение
    val ra: String,            // Right Ascension (Прямое восхождение)
    val dec: String,           // Declination (Склонение)
    val constellation: String, // Const (Созвездие)
    val majAx: Double?,        // MajAx (Большая ось в угловых минутах)
    val minAx: Double?,        // MinAx (Малая ось в угловых минутах)
    val posAng: Int?,          // PosAng (Позиционный угол в градусах)

    // Звездные величины (Фотометрия)
    val bMag: Double?,         // B-Mag (Яркость в синем фильтре)
    val vMag: Double?,         // V-Mag (Видимая яркость в визуальном фильтре)
    val jMag: Double?,         // J-Mag (Инфракрасный фильтр J)
    val hMag: Double?,         // H-Mag (Инфракрасный фильтр H)
    val kMag: Double?,         // K-Mag (Инфракрасный фильтр K)
    val surfBr: Double?,       // SurfBr (Поверхностная яркость)

    // Морфология и кинематика
    val hubble: String?,       // Hubble (Тип галактики по Хабблу)
    val pax: Int?,             // Pax (Параллакс)
    val pmRa: Double?,         // Pm-RA (Собственное движение по RA)
    val pmDec: Double?,        // Pm-Dec (Собственное движение по Dec)
    val radVel: Int?,          // RadVel (Радиальная скорость в км/с)
    val redshift: Double?,     // Redshift (Красное смещение)

    // Центральная звезда (для планетарных туманностей)
    val cstarUMag: Double?,    // Cstar U-Mag (Яркость центральной звезды в U)
    val cstarBMag: Double?,    // Cstar B-Mag (Яркость центральной звезды в B)
    val cstarVMag: Double?,    // Cstar V-Mag (Яркость центральной звезды в V)

    // Идентификаторы и каталоги
    val m: Int?,               // M (Номер по каталогу Мессье)
    val ngc: Int?,             // NGC (Номер по каталогу NGC)
    val ic: Int?,              // IC (Номер по каталогу IC)
    val cstarNames: String?,   // Cstar Names (Имена центральной звезды)
    val identifiers: String?,  // Identifiers (Альтернативные обозначения)
    val commonNames: String?,  // Common names (Популярные названия, например "M42")

    // Заметки и источники
    val nedNotes: String?,     // NED notes (Заметки базы данных NED)
    val openNgcNotes: String?, // OpenNGC notes (Специфичные заметки OpenNGC)
    val sources: String?       // Sources (Источники данных)
)

enum class ObjectType {
    UNKNOW,
    GALAXY,
    NEBULA,
    STAR,
    STAR_GROUP,
}

fun typeFullName(type: String) =  when(type.trim().uppercase()) {
    "RFN" -> "Reflection Nebula"
    "EMN" -> "Emission Nebula"
    "HII" -> "H II Region"
    "PN" -> "Planetary Nebula"
    "SNR" -> "Supernova Remnant"
    "DARKN" -> "Dark Nebula"
    "CL+N" -> "Cluster with Nebula"
    "G" -> "Galaxy"
    "GPAIR" -> "Galaxy Pair"
    "GTRPL" -> "Galaxy Triplet"
    "GGROUP" -> "Galaxy Group"
    "GCL" -> "Galaxy Cluster"
    "OCL" -> "Open Cluster"
    "*ASS" -> "Stellar Association"
    "AST" -> "Asterism"
    "*GROUP" -> "Star Group"
    "*" -> "Single Star"
    "**" -> "Double Star"
    "NONEX" -> "Nonexistent Object"
    else -> "Unknown Type"
}
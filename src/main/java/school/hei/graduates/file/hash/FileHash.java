package school.hei.graduates.file.hash;

import school.hei.graduates.PojaGenerated;

@PojaGenerated
public record FileHash(FileHashAlgorithm algorithm, String value) {}
